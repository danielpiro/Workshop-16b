package Encryption;

public interface IEncrypt {

    public boolean connect();
    public String encrypt(String to_encrypt);
    public String decrypt(String to_decrypt);




}
