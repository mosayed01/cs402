package com.example.demo.ciphers.des.logic.utils;

import com.example.demo.ciphers.des.logic.HexString;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class HexStringTest {

    @Test
    void toBinaryByteArray() {
        // given a hex string
        String hexString = "0123456789ABCDEF";
        // when converting to binary byte array
        HexString hexStringObj = new HexString(hexString);
        byte[] binaryByteArray = hexStringObj.toBinaryByteArray();
        // then return the correct binary byte array
        String binaryString = "0000000100100011010001010110011110001001101010111100110111101111";
        byte[] trueResult = new byte[binaryString.length()];

        for (int i = 0; i < binaryString.length(); i++) {
            trueResult[i] = (byte) (binaryString.charAt(i) - '0');
        }
        assertArrayEquals(trueResult, binaryByteArray);
    }

    @Test
    void fromBinaryByteArray() {
        // given a binary byte array
        String binaryString = "0000000100100011010001010110011110001001101010111100110111101111";
        byte[] binaryByteArray = new byte[binaryString.length()];

        for (int i = 0; i < binaryString.length(); i++) {
            binaryByteArray[i] = (byte) (binaryString.charAt(i) - '0');
        }
        // when converting to hex string
        HexString hexStringObj = HexString.fromBinaryByteArray(binaryByteArray);
        // then return the correct hex string
        String trueResult = "0123456789ABCDEF";
        assertEquals(trueResult, hexStringObj.toString());
    }
}