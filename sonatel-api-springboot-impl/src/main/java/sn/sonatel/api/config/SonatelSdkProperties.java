package sn.sonatel.api.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.model.PublicKey;
import sn.sonatel.api.service.EncryptionService;

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

    private final Security security = new Security();
    private String myMsisdn;
    private String myPinCode;
    private String baseUrl;
    private String publicKeyUri;

    private final PublicKey publicKey ;
    private final String myEncodedPinCode ;

    public SonatelSdkProperties(@Qualifier(value=Constants.Qualifier.WEBCLIENT) WebClient webClient, EncryptionService encryptionService) {
        publicKey = webClient.get()
                .uri(this.getBaseUrl() + this.getPublicKeyUri())
                .retrieve()
                .bodyToMono(PublicKey.class)
                .block();

        myEncodedPinCode = encryptionService.encrypt(myPinCode);
    }

    @Getter
    @Setter
    public static class Security {
        private String clientId;
        private String clientSecret;
    }

}
