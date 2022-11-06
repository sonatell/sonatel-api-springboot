package sn.sonatel.api.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;

@Getter
public class ClientResponseException extends WebClientException {

    private final HttpStatus status;
    private final ErrorDetails errorDetails;

    public ClientResponseException(HttpStatus status, ErrorDetails errorDetails) {
        super(status.getReasonPhrase());
        this.status = status;
        this.errorDetails = errorDetails;
    }

}
