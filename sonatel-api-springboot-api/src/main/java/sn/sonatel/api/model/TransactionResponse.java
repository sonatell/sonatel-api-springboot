package sn.sonatel.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {

    private String transactionId;

    private TransactionStatus status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String requestId;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String reference;

}
