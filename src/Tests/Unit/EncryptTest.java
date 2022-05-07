package Tests.Unit;

import User.Encrypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {

    private static Encrypt enc;

    @BeforeEach
    void setUp() throws IOException {
        enc = new Encrypt();

    }

    @Test
    void encrypt() {
        assertEquals("yf|jjqf", enc.encrypt("taweela"));
        assertEquals("taweela", enc.decrypt(enc.encrypt("taweela")));

    }

    @Test
    void decrypt() {
        assertEquals("taweela", enc.decrypt("yf|jjqf"));
        assertEquals("pass1234", enc.decrypt(enc.encrypt("pass1234")));


    }
}
