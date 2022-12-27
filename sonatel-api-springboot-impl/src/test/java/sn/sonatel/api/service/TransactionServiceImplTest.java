package sn.sonatel.api.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sn.sonatel.api.autoconfigure.SonatelSdkProperties;

class TransactionServiceImplTest {
    /**
     * Method under test: {@link TransactionServiceImpl#getPublicKey()}
     */
    @Test
    void testGetPublicKey() {
        EncryptionServiceImpl encryptionService = new EncryptionServiceImpl(null, new SonatelSdkProperties());
        assertNull((new TransactionServiceImpl(encryptionService, null, new SonatelSdkProperties())).getPublicKey());
    }

}

