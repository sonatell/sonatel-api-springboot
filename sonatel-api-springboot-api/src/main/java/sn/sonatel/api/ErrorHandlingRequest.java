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
package sn.sonatel.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import sn.sonatel.api.exceptions.ApiException;
import sn.sonatel.api.exceptions.RequestErrorHandler;

import java.util.Objects;


/**
 * @author Komi Serge Innocent
 */
public final class ErrorHandlingRequest<T extends ApiException> {

	private static final Logger log = LoggerFactory.getLogger(ErrorHandlingRequest.class);

	private final RestOperations restOperations;

	private final RequestErrorHandler<T> errorHandler;

	private WebClient webClient;

	public ErrorHandlingRequest(RestOperations restOperations, RequestErrorHandler<T> errorHandler) {
		Assert.notNull(restOperations, "restOperations cannot be null must not be null");
		Assert.notNull(errorHandler, "errorHandler must not be null");
		this.restOperations = restOperations;
		this.errorHandler = errorHandler;
	}


	public <V> V execute(RequestEntity<?> requestInfo, Class<V> responseType) throws T {

		if (log.isTraceEnabled()) {
			log.trace("Sending client request {} to API ", requestInfo);
		}

		try {

			webClient
					.method(HttpMethod.POST)
					//.ur
					.body(Mono.just(Objects.requireNonNull(requestInfo.getBody())),responseType)
					.retrieve()
					.toEntity(responseType)
					//.bodyToMono(responseType)
					.block();

			return this.restOperations.exchange(requestInfo, responseType).getBody();

		}
		catch (ResourceAccessException exception) {
			throw this.errorHandler.handleIOException(exception);
		}
		catch (WebClientException exception) {

			throw this.errorHandler.handleHttpResponseException(exception);

		}
	}
}
