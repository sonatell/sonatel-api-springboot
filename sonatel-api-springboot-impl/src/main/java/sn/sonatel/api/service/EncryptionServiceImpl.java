package sn.sonatel.api.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import sn.sonatel.api.model.KeyType;

@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

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

}
