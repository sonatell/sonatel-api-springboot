package sn.sonatel.api.model;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    SUCCESS,
    INITIATED,
    PENDING,
    FAILED,
    ACCEPTED,
    REJECTED,
    CANCELLED;
}
