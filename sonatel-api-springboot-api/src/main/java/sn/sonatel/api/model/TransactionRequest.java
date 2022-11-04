package sn.sonatel.api.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class TransactionRequest {
    private String partnerMsisdn;
    private String partnerPinCode;

    @NonNull
    private Float amount;

    @NonNull
    private String customerMsisdn;

    private Map<String, String> metadata = new HashMap<>();

    private String reference;

    private boolean receivedNotification;
}
