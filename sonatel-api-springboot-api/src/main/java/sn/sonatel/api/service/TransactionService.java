package sn.sonatel.api.service;

import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;
import sn.sonatel.api.model.exception.ClientResponseException;

public interface TransactionService {

        PublicKey getPublicKey();

        Float getRetailerBalance() throws ClientResponseException;

        Float getMerchantBalance() throws ClientResponseException;

        TransactionResponse cashIn(TransactionRequest request) throws ClientResponseException;
        TransactionResponse webPayment(TransactionRequest request) throws ClientResponseException;

}
