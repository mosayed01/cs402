package com.example.demo.ciphers.des.logic.utils;

import java.util.Arrays;

public class Utils {
    public static byte[] hexStringToBinary(String hexString) {
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Length of hexString must be an even number");
        }

        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < hexString.length(); i++) {
            int decimal = Integer.parseInt(hexString.substring(i, i + 1), 16);
            StringBuilder binary = new StringBuilder(Integer.toBinaryString(decimal));
            while (binary.length() < 4) {
                binary.insert(0, "0");
            }
            binaryString.append(binary);
        }
        byte[] bytes = new byte[binaryString.length()];
        for (int i = 0; i < binaryString.length(); i++) {
            bytes[i] = (byte) (binaryString.charAt(i) - '0');
        }
        return bytes;
    }

    public static String binaryToHexString(byte[] bytes) {
        if (bytes.length % 4 != 0) {
            throw new IllegalArgumentException("Length of bytes must be a multiple of 4");
        }

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bytes.length; i += 4) {
            int decimal = 0;
            for (int j = 0; j < 4; j++) {
                decimal = decimal * 2 + bytes[i + j];
            }
            hexString.append(Integer.toHexString(decimal).toUpperCase());
        }
        return hexString.toString();
    }

    public static byte[] xor(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Length of a and b must be the same");
        }
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public static byte[] leftShift(byte[] bytes, int shift) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[(i + shift) % bytes.length];
        }
        return result;
    }

    public static byte[] permute(byte[] bytes, int[] table) {
        byte[] result = new byte[table.length];
        for (int i = 0; i < table.length; i++) {
            result[i] = bytes[table[i] - 1];
        }
        return result;
    }

    public static void main(String[] args) {
        String hexString = "18CA18AD5A78E394";
        byte[] bytes = hexStringToBinary(hexString);
        System.out.println(Arrays.toString(hexStringToBinary(hexString)) + " " + bytes.length);
        System.out.println(binaryToHexString(bytes) + " " + bytes.length);
    }

}
