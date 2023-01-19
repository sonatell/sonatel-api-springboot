package sn.sonatel.api.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import sn.sonatel.api.autoconfigure.SonatelSdkProperties;
import sn.sonatel.api.model.exception.ClientResponseException;

class TransactionServiceImplTest {
    /**
     * Method under test: {@link TransactionServiceImpl#getPublicKey()}
     */

    WebClient webClient = Mockito.mock(WebClient.class);

    @Test
    void testGetPublicKey() {
        EncryptionServiceImpl encryptionService = new EncryptionServiceImpl(null, new SonatelSdkProperties());
        assertNull((new TransactionServiceImpl(encryptionService, null, new SonatelSdkProperties())).getPublicKey());
    }

    /**
     * Method under test: {@link TransactionServiceImpl#getRetailerBalance()}
     */
    @Test
    @Disabled
    void testGetRetailerBalance() throws ClientResponseException {

        EncryptionServiceImpl encryptionService = new EncryptionServiceImpl(webClient, new SonatelSdkProperties());

        (new TransactionServiceImpl(encryptionService, webClient, new SonatelSdkProperties())).getRetailerBalance();
    }

    /**
     * Method under test: {@link TransactionServiceImpl#getMerchantBalance()}
     */
    @Test
    @Disabled
    void testGetMerchantBalance() throws ClientResponseException {
        EncryptionServiceImpl encryptionService = new EncryptionServiceImpl(webClient, new SonatelSdkProperties());

        (new TransactionServiceImpl(encryptionService, webClient, new SonatelSdkProperties())).getMerchantBalance();
    }


}

