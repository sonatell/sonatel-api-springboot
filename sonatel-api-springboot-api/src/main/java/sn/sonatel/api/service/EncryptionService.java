package sn.sonatel.api.service;

import sn.sonatel.api.model.PublicKey;

public interface EncryptionService {

    String encrypt(String message) throws IllegalArgumentException;
    PublicKey getPublicKey();
    String getMyEncodedPinCode();

}
