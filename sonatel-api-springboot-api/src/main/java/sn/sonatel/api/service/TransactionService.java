package sn.sonatel.api.service;

import sn.sonatel.api.model.TransactionRequest;
import sn.sonatel.api.model.TransactionResponse;

public interface TransactionService {
        TransactionResponse cashIn(TransactionRequest request);
}
