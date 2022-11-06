package sn.sonatel.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.sonatel.api.autoconfigure.Constants;
import sn.sonatel.api.autoconfigure.SonatelSdkProperties;
import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.model.Transaction;
import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;
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
    public TransactionResponse cashIn(TransactionRequest request) {
        var transaction = RequestMapper.mapTransactionRequest(request, this.sonatelSdkProperties.getMyMsisdn(), this.encryptionService.getMyEncodedPinCode());
        log.info("Sending request {}", transaction);
        return this.sendRequest(transaction, this.sonatelSdkProperties.getCashinUri());
    }

    @Override
    public PublicKey getPublicKey() {
        return encryptionService.getPublicKey();
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
}
