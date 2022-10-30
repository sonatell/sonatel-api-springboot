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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;


/**
 * @author Komi Serge Innocent
 */
public class ApiException extends Exception {

	private final ErrorCode errorCode;

	private final ApiError apiError;

	public ApiException(
			@NonNull ErrorCode errorCode,
			@NonNull String message,
			@Nullable Throwable cause) {
		this(errorCode, message, cause, null);
	}

	public ApiException(
			@NonNull ErrorCode errorCode,
			@NonNull String message,
			@Nullable Throwable cause,
			@Nullable ApiError apiError
	) {
		super(message, cause);
		Assert.notNull(message, () -> "message must not be null or empty");
		Assert.notNull(errorCode, "errorCode must not be null");
		this.errorCode = errorCode;
		this.apiError = apiError;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public ApiError getApiError() {
		return apiError;
	}
}
