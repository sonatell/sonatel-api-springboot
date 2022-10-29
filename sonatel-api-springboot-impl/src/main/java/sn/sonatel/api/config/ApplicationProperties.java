package sn.sonatel.api.config;

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
public class ApplicationProperties {

    private final Security security = new Security();
    private String myMsisdn;
    private String myPinCode;

    private String baseUrl;
    private String publicKeyUri;

    @Getter
    @Setter
    public static class Security {
        private String clientId;
        private String clientSecret;
    }

}
