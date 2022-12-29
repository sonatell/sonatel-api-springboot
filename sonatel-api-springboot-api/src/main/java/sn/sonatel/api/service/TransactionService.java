package sn.sonatel.api.service;

import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;
import sn.sonatel.api.model.exception.ClientResponseException;

public interface TransactionService {

        PublicKey getPublicKey();
        Float getBalance() throws ClientResponseException;
        TransactionResponse cashIn(TransactionRequest request) throws ClientResponseException;

}
