package User;

public class Encrypt {

    public String encrypt(String to_encrypt) {

        char[] chars = to_encrypt.toCharArray();
        StringBuilder st = new StringBuilder();
        for(char c: chars){
            st.append((char) (c + 5));
        }
        return st.toString();
    }

    public String decrypt(String to_decrypt) {
        char[] chars1 = to_decrypt.toCharArray();
        StringBuilder st = new StringBuilder();
        for (char c : chars1) {
            st.append((char) (c + 5));
        }
        return st.toString();
    }

    }
