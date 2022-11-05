package sn.sonatel.api.model;

import lombok.Getter;
import lombok.Setter;

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
}
