package sn.sonatel.api.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction implements Serializable {

    private RelatedParty partner;

    private RelatedParty customer;

    private Money amount;

    private Instant requestDate;

    private Boolean receiveNotification = true;

    private String reference;

    private Map<String, String> metadata = new HashMap<>();

    @Override
    public String toString() {
        return "{" +
                "partner=" + partner +
                ", customer=" + customer +
                ", amount=" + amount +
                ", requestDate=" + requestDate +
                ", receiveNotification=" + receiveNotification +
                ", reference='" + reference + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
