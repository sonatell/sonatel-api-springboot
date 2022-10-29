package sn.sonatel.api.service;

public interface EncryptionService {

    String encrypt(String message, String publicKey) throws IllegalArgumentException;

}
