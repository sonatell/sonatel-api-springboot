package sn.sonatel.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicKey {

    private String keyId;
    private KeyType keyType;
    private int keySize;
    private String key;

    @Override
    public String toString() {
        return "PublicKey{" +
                "keyId='" + keyId + '\'' +
                ", keyType=" + keyType +
                ", keySize=" + keySize +
                ", key='" + key + '\'' +
                '}';
    }
}
