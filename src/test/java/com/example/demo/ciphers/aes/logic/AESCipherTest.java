package com.example.demo.ciphers.aes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESCipherTest {

    @Test
    void testEncrypt() {
        AESCipher aesCipher = new AESCipher();
        String input = "Two One Nine Two";
        String key = "Thats my Kung Fu";
        String expected = "29C3505F571420F6402299B31A02D73A";
        assertEquals(expected, aesCipher.encrypt(input, key).toUpperCase());
    }
}