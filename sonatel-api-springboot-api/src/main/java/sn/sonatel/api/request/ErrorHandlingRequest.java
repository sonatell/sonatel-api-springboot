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
package sn.sonatel.api.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import sn.sonatel.api.exceptions.ApiErrorHandler;
import sn.sonatel.api.exceptions.ApiException;

import java.nio.charset.StandardCharsets;


/**
 * @author Komi Serge Innocent
 */
public final class ErrorHandlingRequest<T extends ApiException> {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingRequest.class);

    private final ApiErrorHandler<T> errorHandler;

    private final WebClient webClient;

    public ErrorHandlingRequest(ApiErrorHandler<T> errorHandler, WebClient webClient) {
        Assert.notNull(webClient, "webClient cannot be null must not be null");
        Assert.notNull(errorHandler, "errorHandler must not be null");
        this.webClient = webClient;
        this.errorHandler = errorHandler;
    }


    public <V> V execute(RequestEntity<?> requestInfo, Class<V> responseType) throws T {

        if (log.isTraceEnabled()) {
            log.trace("Sending client request {} to API ", requestInfo);
        }

        try {

            return webClient
                    .method(requestInfo.getMethod())
                    .uri(requestInfo.getUrl())
                    .headers(httpHeaders -> httpHeaders.addAll(requestInfo.getHeaders()))
                    .bodyValue(requestInfo.getBody() != null ? BodyInserters.fromValue(requestInfo.getBody()) : BodyInserters.empty())
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

        } catch (WebClientException exception) {

            throw this.errorHandler.handleHttpResponseException(exception);

        } catch (OAuth2AuthorizationException exception) {
            var apiException = WebClientResponseException.create(HttpStatus.UNAUTHORIZED.value(), exception.getError().getDescription(), requestInfo.getHeaders(), exception.getLocalizedMessage().getBytes(StandardCharsets.UTF_8), null);
            throw this.errorHandler.handleHttpResponseException(apiException);
        } catch (Exception exception) {
            throw this.errorHandler.handleUnknownException(exception);
        }
    }
}
