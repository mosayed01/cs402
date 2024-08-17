package com.example.demo.ciphers.des.logic;

import java.util.Arrays;

import static com.example.demo.utils.Utils.binaryToHexString;
import static com.example.demo.utils.Utils.hexStringToBinary;

public class HexString {
    private final String hexString;

    public HexString(String hexString) {
        if (!hexString.matches("[0-9A-Fa-f]+")) {
            throw new IllegalArgumentException("hexString must contain only hexadecimal characters");
        }
        this.hexString = hexString;
    }

    public byte[] toBinaryByteArray() {
        return hexStringToBinary(hexString);
    }

    public int[] toBytes() {
        byte[] bytes = toBinaryByteArray();
        int[] result = new int[bytes.length / 8];
        for (int i = 0; i < result.length; i++) {
            int decimal = 0;
            for (int j = 0; j < 8; j++) {
                decimal = decimal * 2 + bytes[i * 8 + j];
            }
            result[i] = decimal;
        }
        return result;
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
