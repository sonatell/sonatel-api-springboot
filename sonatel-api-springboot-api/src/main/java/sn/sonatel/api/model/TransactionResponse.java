package sn.sonatel.api.model;

import lombok.Builder;

@Builder
public class TransactionResponse {

    private String reference;

    private String description;
}
