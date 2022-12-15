package sn.sonatel.api.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.autoconfigure.SonatelSdkProperties;

class EncryptionServiceImplTest {

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link EncryptionServiceImpl#EncryptionServiceImpl(WebClient, SonatelSdkProperties)}
     *   <li>{@link EncryptionServiceImpl#getMyEncodedPinCode()}
     *   <li>{@link EncryptionServiceImpl#getPublicKey()}
     * </ul>
     */
    @Test
    void testConstructor() {
        SonatelSdkProperties sonatelSdkProperties = new SonatelSdkProperties();
        sonatelSdkProperties.setBaseUrl("https://example.org/example");
        sonatelSdkProperties.setCashinUri("Cashin Uri");
        sonatelSdkProperties.setEnabled(true);
        sonatelSdkProperties.setMyMsisdn("My Msisdn");
        sonatelSdkProperties.setMyPinCode("My Pin Code");
        sonatelSdkProperties.setPublicKeyUri("Public Key Uri");
        EncryptionServiceImpl actualEncryptionServiceImpl = new EncryptionServiceImpl(null, sonatelSdkProperties);

        assertNull(actualEncryptionServiceImpl.getMyEncodedPinCode());
        assertNull(actualEncryptionServiceImpl.getPublicKey());
    }

}

