package sn.sonatel.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.config.Constants;
import sn.sonatel.api.config.SonatelSdkProperties;
import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final WebClient webClient;
    private final SonatelSdkProperties properties;

    public TransactionServiceImpl(@Qualifier(value= Constants.Qualifier.WEBCLIENT) WebClient webClient, SonatelSdkProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    @Override
    public TransactionResponse cashIn(TransactionRequest request) {
        return new TransactionResponse();
    }

    @Override
    public PublicKey getPublicKey() {
        return properties.getPublicKey();
    }
}
