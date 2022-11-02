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
package sn.sonatel.api.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import sn.sonatel.api.config.ApplicationProperties;
import sn.sonatel.api.exceptions.ApiErrorHandler;
import sn.sonatel.api.exceptions.ApiException;
import sn.sonatel.api.model.KeyType;
import sn.sonatel.api.request.ErrorHandlingRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

    private ApiPublicKey apiPublicKey;
    private final WebClient webClient;
    private final ApplicationProperties applicationProperties;
    private final ApiErrorHandler<ApiException> apiErrorHandler;

    public EncryptionServiceImpl(WebClient webClient, ApplicationProperties applicationProperties, ApiErrorHandler<ApiException> apiErrorHandler) {
        this.webClient = webClient;
        this.applicationProperties=applicationProperties;
        this.apiErrorHandler = apiErrorHandler;
    }


    public String getPubicKey() throws ApiException {

        if (apiPublicKey != null) {
            return apiPublicKey.getKey();
        }

        RequestEntity<Void> request = RequestEntity.method(HttpMethod.GET,
                UriComponentsBuilder.fromHttpUrl(applicationProperties.getBaseUrl())
                        .path(applicationProperties.getPublicKeyUri())
                        .build().toUri())
                .build();

        ErrorHandlingRequest<ApiException> httpRequest = new ErrorHandlingRequest<>(apiErrorHandler, this.webClient);
        apiPublicKey = httpRequest.execute(request, ApiPublicKey.class);

        return apiPublicKey.getKey();
    }

    public String encrypt(String message, String publicKey) throws IllegalArgumentException {
        try {
            var cipher = Cipher.getInstance(KeyType.RSA.toString()); //NOSONAR

            var publicKeyBytes = Base64.decodeBase64(publicKey);
            KeyFactory publicKeyFactory = KeyFactory.getInstance(KeyType.RSA.toString());
            var publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            var key = publicKeyFactory.generatePublic(publicKeySpec);

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64String(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch(NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException ex){
            log.error("Failed to encrypt message", ex);
            throw new IllegalArgumentException(ex);
        }
    }

    @Getter
    private static class ApiPublicKey {

        private String key;

        private String keyId;

        private int keySize;

        private String keyType;
    }

}
