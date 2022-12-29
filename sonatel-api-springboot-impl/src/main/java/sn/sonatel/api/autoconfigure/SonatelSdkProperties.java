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
package sn.sonatel.api.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to the business logic
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "sonatel", ignoreUnknownFields = false)
public class SonatelSdkProperties {

    /**
     * Whether to enable or disable sonatel configuration
     */
    private boolean enabled = true;

    /**
     * security parameters
     */
    private final Security security = new Security();

    /**
     * partner msisdn
     */

    private String myMsisdn;
    /**
     * partner pin code
     */
    private String myPinCode;

    /**
     * sonatel api base url
     */
    private String baseUrl = Constants.DefaultProperties.BASE_URL;

    /**
     * sonatel api public key uri
     */
    private String publicKeyUri = Constants.DefaultProperties.PUBLIC_KEY_URI;

    private String cashinUri = Constants.DefaultProperties.CASHIN_URI;

    private String balanceUri = Constants.DefaultProperties.BALANCE_URI;

    @Getter
    @Setter
    public static class Security {
        /**
         * partner account client id, used to retrieve access_token
         */
        private String clientId;

        /**
         * partner account client secret, used to retrieve access_token
         */
        private String clientSecret;

        /**
         * endpoint to retrieve access token
         */
        private String tokenUrl = Constants.DefaultProperties.TOKEN_URL;
    }

}
