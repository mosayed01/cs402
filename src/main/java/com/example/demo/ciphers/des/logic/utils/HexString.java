package com.example.demo.ciphers.des.logic.utils;

import java.util.Arrays;

import static com.example.demo.ciphers.des.logic.utils.Utils.binaryToHexString;
import static com.example.demo.ciphers.des.logic.utils.Utils.hexStringToBinary;

public class HexString {
    private final String hexString;

    public HexString(String hexString) {
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Length of hexString must be an even number");
        }
        if (!hexString.matches("[0-9A-Fa-f]+")) {
            throw new IllegalArgumentException("hexString must contain only hexadecimal characters");
        }
        this.hexString = hexString;
    }

    public byte[] toBinaryByteArray() {
        return hexStringToBinary(hexString);
    }

    public static HexString fromBinaryByteArray(byte[] bytes) {
        return new HexString(binaryToHexString(bytes));
    }

    @Override
    public String toString() {
        return hexString;
    }

    public static void main(String[] args) {
        HexString hexString = new HexString("0123456789ABCDEF");
        System.out.println(hexString);
        byte[] bytes = hexString.toBinaryByteArray();
        System.out.println(Arrays.toString(bytes));
        System.out.println(binaryToHexString(bytes));
        System.out.println(HexString.fromBinaryByteArray(bytes));
    }
}
