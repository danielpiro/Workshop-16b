package User;

public class Encrypt {

    public String encrypt(String to_encrypt) {
        //todo StringBuilder

        char[] chars = to_encrypt.toCharArray();
        String str ="";
        for(char c: chars){
            str += (char)(c+5);
        }
        return str;
    }

    public String decrypt(String to_decrypt) {
        char[] chars1 = to_decrypt.toCharArray();
        String str = "";
        for (char c : chars1) {
            str += (char)(c - 5);
        }
        return str;
    }

    }
