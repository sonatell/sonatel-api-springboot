package sn.sonatel.api.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.autoconfigure.SonatelSdkProperties;

class EncryptionServiceImplTest {

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link EncryptionServiceImpl#EncryptionServiceImpl(WebClient, SonatelSdkProperties)}
     *   <li>{@link EncryptionServiceImpl#getRetailerAccountEncodedPinCode()}
     *   <li>{@link EncryptionServiceImpl#getPublicKey()}
     * </ul>
     */
    @Test
    void testConstructor() {
        SonatelSdkProperties sonatelSdkProperties = new SonatelSdkProperties();
        sonatelSdkProperties.setBaseUrl("https://example.org/example");
        sonatelSdkProperties.setCashinUri("Cashin Uri");
        sonatelSdkProperties.setEnabled(true);

        var retailer = new SonatelSdkProperties.Account();
        retailer.setMsisdn("My Msisdn 1 ");
        retailer.setPinCode("My Pin Code 1");
        sonatelSdkProperties.setMerchant(retailer);

        var merchant = new SonatelSdkProperties.Account();
        merchant.setMsisdn("My Msisdn 2");
        merchant.setPinCode("My Pin Code 2");
        sonatelSdkProperties.setMerchant(merchant);
        sonatelSdkProperties.setPublicKeyUri("Public Key Uri");
        EncryptionServiceImpl actualEncryptionServiceImpl = new EncryptionServiceImpl(null, sonatelSdkProperties);

        assertNull(actualEncryptionServiceImpl.getRetailerAccountEncodedPinCode());
        assertNull(actualEncryptionServiceImpl.getPublicKey());
    }

}

