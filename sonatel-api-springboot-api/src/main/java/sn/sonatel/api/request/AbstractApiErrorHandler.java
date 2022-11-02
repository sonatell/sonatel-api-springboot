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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClientException;
import sn.sonatel.api.exceptions.ApiError;
import sn.sonatel.api.exceptions.ApiException;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Komi Serge Innocent
 */
public abstract class AbstractApiErrorHandler<T extends ApiException> extends
		AbstractRequestErrorHandler<T> {

	protected final ObjectMapper objectMapper;

	protected AbstractApiErrorHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	protected final ApiException httpResponseErrorToBaseException(
			WebClientException e) {
		ApiException base = super.httpResponseErrorToBaseException(e);
		ApiError parsedError = this.parseErrorResponse(base.getMessage()).orElse(null);
		return  ApiException.createException(base.getErrorCode(), base.getMessage(), e.getCause(), parsedError);
	}

	private Optional<ApiError> parseErrorResponse(String content) {
		ApiError response = null;

		if (content != null && !content.isEmpty()) {
			try {
				response = objectMapper.readValue(content.substring(content.indexOf("{")), ApiError.class);
			}
			catch (IOException | IndexOutOfBoundsException e) {
				// Ignore any error that may occur while parsing the error response. The server
				// may have responded with a non-json payload. Return an empty return value, and
				// let the base class logic come into play.
			}
		}

		return Optional.ofNullable(response);
	}
}
