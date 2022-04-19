package Encryption;

public class EncryptImp implements IEncrypt {
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public String encrypt(String to_encrypt) {
        return to_encrypt;
    }

    @Override
    public String decrypt(String to_decrypt) {
        return to_decrypt;
    }
}
