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

        public static final String TOKEN_URL = BASE_URL + "/oauth/v1/token";

        private DefaultProperties() {
        }
    }
    private Constants() {
    }
}
