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

import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.UnknownHttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

/**
 * An abstract RequestErrorHandler implementation that maps HTTP status codes to Api error codes.
 * Also provides reasonable default implementations to other error handler methods in the
 * RequestErrorHandler interface.
 *
 * @author Komi Serge Innocent
 */
public abstract class AbstractRequestErrorHandler<T extends ApiException> implements
		RequestErrorHandler<T> {


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
	public T handleIOException(ResourceAccessException e) {
		ApiException apiException = this.ioErrorToBaseException(e);
		return this.createException(apiException);
	}

	@Override
	public T handleHttpResponseException(WebClientException e) {
		ApiException base = this.httpResponseErrorToBaseException(e);
		return this.createException(base);
	}

	@Override
	public T handleParseException(IOException e) {
		return null;
	}


	protected ApiException ioErrorToBaseException(ResourceAccessException e) {
		ErrorCode code = ErrorCode.UNKNOWN;
		String message = "Unknown error while making a remote service call";
		if (isInstance(e, SocketTimeoutException.class)) {
			code = ErrorCode.DEADLINE_EXCEEDED;
			message = "Timed out while making an API call";
		}

		if (isInstance(e, UnknownHostException.class) || isInstance(e, NoRouteToHostException.class)) {
			code = ErrorCode.UNAVAILABLE;
			message = "Failed to establish a connection";
		}

		return new ApiException(code, message, e);
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

		return new ApiException(code, message, e);

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
