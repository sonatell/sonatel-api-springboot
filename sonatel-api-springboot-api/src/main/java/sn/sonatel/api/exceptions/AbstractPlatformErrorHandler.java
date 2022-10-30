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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;

/**
 * @author Komi Serge Innocent
 */
public abstract class AbstractPlatformErrorHandler<T extends ApiException> extends
		AbstractRequestErrorHandler<T> {

	protected final ObjectMapper objectMapper;

	protected AbstractPlatformErrorHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	protected final ApiException httpResponseErrorToBaseException(
			WebClientException e) {
		ApiException base = super.httpResponseErrorToBaseException(e);
		ApiError parsedError = this.parseErrorResponse(base.getMessage());

		ErrorCode code = base.getErrorCode();

		String message = parsedError.getDetail() != null ? parsedError.getDetail() : base.getMessage();

		return new ApiException(code, message, e, parsedError);
	}

	private ApiError parseErrorResponse(String content) {
		ApiError response = ApiError.builder().build();

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

		return response;
	}
}
