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

public final class Constants {

    public static final class Qualifier {
        public static final String WEBCLIENT = "SONATEL";

        private Qualifier() {
        }
    }

    public static final class DefaultProperties {
        public static final String BASE_URL = "https://api.sandbox.orange-sonatel.com";
        public static final String API_URI = "/api/eWallet/v1";

        public static final String PUBLIC_KEY_URI = "/api/account/v1/publicKeys";

        public static final String CASHIN_URI = API_URI + "/cashins";

        public static final String WEB_PAYMENT_URI = API_URI + "/payments/onestep";

        public static final String BALANCE_URI = API_URI + "/account/retailer/balance";

        public static final String TOKEN_URL = BASE_URL + "/oauth/v1/token";

        private DefaultProperties() {
        }
    }
    private Constants() {
    }
}
