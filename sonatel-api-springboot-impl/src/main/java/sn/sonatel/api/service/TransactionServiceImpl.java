package sn.sonatel.api.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.autoconfigure.Constants;
import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;

public class TransactionServiceImpl implements TransactionService {

    private final WebClient webClient;
    private final EncryptionService encryptionService;

    public TransactionServiceImpl(EncryptionService encryptionService, @Qualifier(value= Constants.Qualifier.WEBCLIENT) WebClient webClient) {
        this.webClient = webClient;
        this.encryptionService = encryptionService;
    }

    @Override
    public TransactionResponse cashIn(TransactionRequest request) {
        return new TransactionResponse();
    }

    @Override
    public PublicKey getPublicKey() {
        return encryptionService.getPublicKey();
    }
}
