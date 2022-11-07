/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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