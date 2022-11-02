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
import org.springframework.web.reactive.function.client.UnknownHttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import sn.sonatel.api.exceptions.ApiException;
import sn.sonatel.api.exceptions.ErrorCode;
import sn.sonatel.api.exceptions.ApiErrorHandler;

import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Map.entry;

/**
 * An abstract ApiErrorHandler implementation that maps HTTP status codes to Api error codes.
 * Also provides reasonable default implementations to other error handler methods in the
 * ApiErrorHandler interface.
 *
 * @author Komi Serge Innocent
 */
abstract class AbstractRequestErrorHandler<T extends ApiException> implements
		ApiErrorHandler<T> {

	private static final Logger log = LoggerFactory.getLogger(AbstractRequestErrorHandler.class);


	private static final Map<Integer, ErrorCode> HTTP_ERROR_CODES =
			Map.ofEntries(
					entry(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_ARGUMENT),
					entry(HttpStatus.UNAUTHORIZED.value(), ErrorCode.UNAUTHENTICATED),
					entry(HttpStatus.FORBIDDEN.value(), ErrorCode.PERMISSION_DENIED),
					entry(HttpStatus.NOT_FOUND.value(), ErrorCode.NOT_FOUND),
					entry(HttpStatus.CONFLICT.value(), ErrorCode.CONFLICT),
					entry(HttpStatus.TOO_MANY_REQUESTS.value(), ErrorCode.RESOURCE_EXHAUSTED),
					entry(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL),
					entry(HttpStatus.SERVICE_UNAVAILABLE.value(), ErrorCode.UNAVAILABLE)
			);


	@Override
	public T handleHttpResponseException(WebClientException e) {

		if (isInstance(e, SocketTimeoutException.class)) {
			var code = ErrorCode.DEADLINE_EXCEEDED;
			var message = "Timed out while making an API call";
			return this.createException ( ApiException.createException(code, message, e.getCause()));

		}

		if (isInstance(e, UnknownHostException.class) || isInstance(e, NoRouteToHostException.class)) {
			var code = ErrorCode.UNAVAILABLE;
			var message = "Failed to establish a connection";
			return this.createException ( ApiException.createException(code, message, e.getCause()));

		}

		ApiException base = this.httpResponseErrorToBaseException(e);
		return this.createException(base);
	}


	protected ApiException httpResponseErrorToBaseException(
			WebClientException e) {

		ErrorCode code = null;
		int httpStatus = 0;
		String responseBody = null;

		if (e instanceof WebClientResponseException) {
			var execption = (WebClientResponseException) e;
			httpStatus = execption.getRawStatusCode();
			code = HTTP_ERROR_CODES.get(httpStatus);
			responseBody = execption.getResponseBodyAsString();

		}

		if (e instanceof WebClientRequestException) {
			var execption = (WebClientRequestException) e;
			//httpStatus = execption.getRawStatusCode();
			code = HTTP_ERROR_CODES.get(httpStatus);
			//responseBody = execption.getResponseBodyAsString();

		}

		if (e instanceof UnknownHttpStatusCodeException) {

			var execption = (UnknownHttpStatusCodeException) e;
			httpStatus = execption.getRawStatusCode();
			code = HTTP_ERROR_CODES.get(httpStatus);
			responseBody = execption.getResponseBodyAsString();

		}

		if (code == null) {
			code = ErrorCode.UNKNOWN;
		}

		String message = String.format("Unexpected HTTP response with status: %d\n%s",
				httpStatus, responseBody);

		return  ApiException.createException(code, message, Objects.isNull(e.getCause())?e:e.getCause());

	}

	@Override
	public T handleUnknownException(Throwable exception) {
		if (log.isTraceEnabled()){
			log.error("Initial exception thrown ",exception);
		}
		return this.createException(ApiException.createException(ErrorCode.UNKNOWN,exception.getMessage(),null));
	}

	protected abstract T createException(ApiException base);

	private <U> boolean isInstance(Exception t, Class<U> type) {
		Throwable current = t;
		Set<Throwable> chain = new HashSet<>();
		while (current != null) {
			if (!chain.add(current)) {
				break;
			}

			if (type.isInstance(current)) {
				return true;
			}

			current = current.getCause();
		}

		return false;
	}
}
