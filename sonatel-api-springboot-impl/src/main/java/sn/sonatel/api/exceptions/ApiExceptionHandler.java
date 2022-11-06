package sn.sonatel.api.exceptions;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import sn.sonatel.api.model.exception.ClientResponseException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = { ClientResponseException.class })
    protected ResponseEntity<Object> handleException(ClientResponseException ex, WebRequest request) {
        var headers = new HttpHeaders();
         var headerNames = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(request.getHeaderNames(), Spliterator.ORDERED),
                false);
        headerNames.forEach(
                 i-> headers.add(i, request.getHeader(i))
        );
        return handleExceptionInternal(ex, ex.getErrorDetails(), headers, ex.getStatus(), request);
    }
}