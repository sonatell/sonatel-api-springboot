package sn.sonatel.api.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class TransactionRequest {
    private String partnerMsisdn;
    private String partnerEncryptedPinCode;

    @NonNull
    private Float amount;

    @NonNull
    private String customerMsisdn;

    private Map<String, String> metadata = new HashMap<>();

    private String reference;

    private Instant requestDate;

    private boolean receivedNotification = true;

}
