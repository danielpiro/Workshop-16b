package User;

public class Encrypt {

    public String encrypt(String to_encrypt) {
        return "**".concat(to_encrypt).concat("**");
    }

    public String decrypt(String to_decrypt) {
        return to_decrypt.substring(2,to_decrypt.length()-2);
    }
}
