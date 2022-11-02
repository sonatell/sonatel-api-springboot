package sn.sonatel.api.model.exception;

import lombok.Getter;

@Getter
public final class Violation {
    private final String field;
    private final String message;

    public Violation(final String field, final String message) {
        this.field = field;
        this.message = message;
    }
}