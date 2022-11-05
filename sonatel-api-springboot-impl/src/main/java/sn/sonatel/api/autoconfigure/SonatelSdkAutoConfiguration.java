package sn.sonatel.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.service.EncryptionService;
import sn.sonatel.api.service.EncryptionServiceImpl;
import sn.sonatel.api.service.TransactionService;
import sn.sonatel.api.service.TransactionServiceImpl;

/**
 * Sonatel SDK configurations
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({SonatelSdkProperties.class})
@ConditionalOnProperty(prefix = "sonatel", name = "enable", havingValue = "true", matchIfMissing = true)
public class SonatelSdkAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    static class BuilderConfiguration {

        private final SonatelSdkProperties properties;

        BuilderConfiguration(SonatelSdkProperties properties) {
            this.properties = properties;
        }

        private static final String ID = "sonatel";

        @Bean
        public ReactiveClientRegistrationRepository clientRegistrations() {
            ClientRegistration registration = ClientRegistration
                    .withRegistrationId(ID)
                    .tokenUri(properties.getSecurity().getTokenUrl())
                    .clientId(properties.getSecurity().getClientId())
                    .clientSecret(properties.getSecurity().getClientSecret())
                    .scope("email")
                    .authorizationGrantType(new AuthorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue()))
                    .build();
            return new InMemoryReactiveClientRegistrationRepository(registration);
        }

        /**
         * Configure http client usd-ed to consume APIs
         * It manages under the hood, oauth token retrieval et renewal when expired
         * @param clientRegistrations
         * @return
         */
        @Bean
        @Qualifier(value= Constants.Qualifier.WEBCLIENT)
        public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
            InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
            AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
            ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
            oauth.setDefaultClientRegistrationId(ID);
            return WebClient.builder()
                    .filter(oauth)
                    .build();

        }

        @Bean
        public EncryptionService encryption(@Qualifier(value=Constants.Qualifier.WEBCLIENT) WebClient webClient, SonatelSdkProperties applicationProperties){
            return new EncryptionServiceImpl(webClient, applicationProperties);
        }

        @Bean
        public TransactionService transaction(EncryptionService encryptionService, WebClient webClient, SonatelSdkProperties applicationProperties){
            return new TransactionServiceImpl(encryptionService, webClient, applicationProperties);
        }

    }

}


