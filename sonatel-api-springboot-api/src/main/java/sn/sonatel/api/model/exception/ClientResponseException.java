package sn.sonatel.api.model.exception;

import lombok.Getter;

@Getter
public class ClientResponseException extends Exception {

    private final ApiError error;

    public ClientResponseException(ApiError error) {
        super(error.getDetail());
        this.error = error;
    }
}
