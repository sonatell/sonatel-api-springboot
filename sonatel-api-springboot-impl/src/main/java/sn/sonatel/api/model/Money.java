package sn.sonatel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class Money {

    public Money() {
    }

    public Money(Float value) {
        this.value = value;
        this.unit = Currency.XOF;
    }

    private Float value;

    private Currency unit = Currency.XOF;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE, false, false, true, null);
    }
}
