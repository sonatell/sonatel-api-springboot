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

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

/**
 * An interface for handling all sorts of exceptions that may occur while making an HTTP call and
 * converting them into some instance of ApiException.
 *
 * @author Komi Serge Innocent
 */
public interface RequestErrorHandler<T extends ApiException> {

	/**
	 * Handle any low-level transport and initialization errors.
	 */
	T handleIOException(ResourceAccessException e);

	/**
	 * Handle HTTP response exceptions (caused by HTTP error responses).
	 */
	T handleHttpResponseException(WebClientException e);

	/**
	 * Handle any errors that may occur while parsing the response payload.
	 */
	T handleParseException(IOException e);
}
