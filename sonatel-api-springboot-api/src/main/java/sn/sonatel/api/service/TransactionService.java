package sn.sonatel.api.service;

import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;
import sn.sonatel.api.model.exception.ClientResponseException;

public interface TransactionService {
        TransactionResponse cashIn(TransactionRequest request) throws ClientResponseException;
        PublicKey getPublicKey();

}
