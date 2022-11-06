package sn.sonatel.api.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE, false, false, true, null);
    }
}
