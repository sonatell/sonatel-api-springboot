package sn.sonatel.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class RelatedParty implements Serializable {

    private String id;

    private IdType idType = IdType.MSISDN;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String encryptedPinCode;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE, false, false, true, null);
    }
}
