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
package sn.sonatel.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.exceptions.ApiErrorHandler;
import sn.sonatel.api.exceptions.ApiException;
import sn.sonatel.api.request.AbstractApiErrorHandler;

@Configuration
public class SdkConfiguration {

    private static final String ID = "sonatel";

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations(
            @Value("${spring.security.oauth2.client.provider.sonatel.token-uri}") String tokenUri,
            @Value("${spring.security.oauth2.client.registration.sonatel.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.sonatel.client-secret}") String clientSecret,
            @Value("${spring.security.oauth2.client.registration.sonatel.scope}") String scope,
            @Value("${spring.security.oauth2.client.registration.sonatel.authorization-grant-type}") String authorizationGrantType

    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId(ID)
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(scope)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
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
    public ApiErrorHandler<ApiException> apiErrorHandler(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder){
        return new AbstractApiErrorHandler<>(jackson2ObjectMapperBuilder) {
            @Override
            protected ApiException createException(ApiException base) {
                return base;
            }
        };
    }

}


