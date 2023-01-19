/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sn.sonatel.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.sonatel.api.autoconfigure.Constants;
import sn.sonatel.api.autoconfigure.SonatelSdkProperties;
import sn.sonatel.api.model.*;
import sn.sonatel.api.model.exception.ErrorDetails;
import sn.sonatel.api.model.exception.ClientResponseException;
import sn.sonatel.api.service.mapper.RequestMapper;

@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final WebClient webClient;
    private final EncryptionService encryptionService;
    private final SonatelSdkProperties sonatelSdkProperties;

    public TransactionServiceImpl(EncryptionService encryptionService, @Qualifier(value= Constants.Qualifier.WEBCLIENT) WebClient webClient, SonatelSdkProperties sonatelSdkProperties) {
        this.webClient = webClient;
        this.encryptionService = encryptionService;
        this.sonatelSdkProperties = sonatelSdkProperties;
    }

    @Override
    public PublicKey getPublicKey() {
        return encryptionService.getPublicKey();
    }

    @Override
    public Float getRetailerBalance() throws ClientResponseException {
        var relatedParty = new RelatedParty();
        relatedParty.setId(this.sonatelSdkProperties.getRetailer().getMsisdn());
        relatedParty.setEncryptedPinCode(this.encryptionService.getRetailerAccountEncodedPinCode());
        return this.getBalance(relatedParty, this.sonatelSdkProperties.getBalanceUri());
    }

    @Override
    public Float getMerchantBalance() throws ClientResponseException {
        var relatedParty = new RelatedParty();
        relatedParty.setId(this.sonatelSdkProperties.getMerchant().getMsisdn());
        relatedParty.setEncryptedPinCode(this.encryptionService.getMerchantAccountEncodedPinCode());
        return this.getBalance(relatedParty, this.sonatelSdkProperties.getBalanceUri());
    }

    @Override
    public TransactionResponse cashIn(TransactionRequest request) {
        var transaction = RequestMapper.mapTransactionRequest(request, this.sonatelSdkProperties.getRetailer().getMsisdn(), this.encryptionService.getRetailerAccountEncodedPinCode());
        log.info("Sending cashin request {}", transaction);
        return this.sendRequest(transaction, this.sonatelSdkProperties.getCashinUri());
    }

    @Override
    public TransactionResponse webPayment(TransactionRequest request) throws ClientResponseException {
        var transaction = RequestMapper.webPayment(request, this.sonatelSdkProperties.getMerchant().getMsisdn(), this.encryptionService.getMerchantAccountEncodedPinCode());
        log.info("Sending web payment request {}", transaction);
        return this.sendRequest(transaction, this.sonatelSdkProperties.getWebPayment());
    }

    private TransactionResponse sendRequest(Transaction request, String uri) {
        return this.webClient
            .post()
            .uri(this.sonatelSdkProperties.getBaseUrl() + uri)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                    HttpStatus::isError,
                    response -> response.bodyToMono(ErrorDetails.class)
                            .flatMap(errorDetails -> {
                                log.error("Failed to process transaction due to : {}", errorDetails);
                                return Mono.error(new ClientResponseException(response.statusCode(), errorDetails));
                            })
            )
            .bodyToMono(TransactionResponse.class)
            .block();
    }


    private Float getBalance(RelatedParty party, String uri) {
        return this.webClient
                .post()
                .uri(this.sonatelSdkProperties.getBaseUrl() + uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(party)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.bodyToMono(ErrorDetails.class)
                                .flatMap(errorDetails -> {
                                    log.error("Failed to process transaction due to : {}", errorDetails);
                                    return Mono.error(new ClientResponseException(response.statusCode(), errorDetails));
                                })
                )
                .bodyToMono(Money.class)
                .block()
                .getValue();
    }
}
