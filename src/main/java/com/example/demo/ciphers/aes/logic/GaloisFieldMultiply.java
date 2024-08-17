package com.example.demo.ciphers.aes.logic;

public class GaloisFieldMultiply {

    private static final int POLYNOMIAL = 0x1B;

    public static int multiply(int a, int b) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) {
                result ^= a;
            }
            boolean carry = (a & 0x80) != 0;
            a = multiplyByX(a);
            if (carry) {
                a ^= POLYNOMIAL;
            }
            b = b >>> 1;
        }
        return result;
    }

    private static int multiplyByX(int a) {
        return (a << 1) & 0xFF;
    }

    public static void main(String[] args) {
        int a = 0x57;       // 0101 0111
        int b = 0x83;       // 1000 0011
        int result = 0xC1;  // 1100 0001
        System.out.printf("Expected: %02X\n", result);
        System.out.printf("Actual: %02X\n", multiply(a, b));
    }
}
