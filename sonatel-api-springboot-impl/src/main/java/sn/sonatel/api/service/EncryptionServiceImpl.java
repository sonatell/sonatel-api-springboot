package sn.sonatel.api.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

import sn.sonatel.api.autoconfigure.SonatelSdkProperties;
import sn.sonatel.api.autoconfigure.Constants;
import sn.sonatel.api.model.KeyType;
import sn.sonatel.api.model.PublicKey;

@Slf4j

//@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

    private PublicKey publicKey ;
    private String myEncodedPinCode ;

    private final SonatelSdkProperties applicationProperties;
    private final WebClient webClient;

    public EncryptionServiceImpl(@Qualifier(value=Constants.Qualifier.WEBCLIENT) WebClient webClient, SonatelSdkProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.webClient = webClient;
    }

    public String encrypt(String message) throws IllegalArgumentException {
        try {
            var cipher = Cipher.getInstance(KeyType.RSA.toString()); //NOSONAR

            var publicKeyBytes = Base64.decodeBase64(this.getPublicKey().getKey());
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

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getMyEncodedPinCode() {
        return myEncodedPinCode;
    }

    @PostConstruct
    void initProperties(){
        publicKey = this.webClient.get()
                .uri(this.applicationProperties.getBaseUrl() + applicationProperties.getPublicKeyUri())
                .retrieve()
                .bodyToMono(PublicKey.class)
                .block();

        myEncodedPinCode = this.encrypt(applicationProperties.getMyPinCode());
    }
}
