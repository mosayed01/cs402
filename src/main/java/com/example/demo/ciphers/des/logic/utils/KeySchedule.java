package com.example.demo.ciphers.des.logic.utils;

import static com.example.demo.ciphers.des.logic.utils.Utils.*;

public class KeySchedule {
    private static final int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4
    };

    private static final int[] PC2 = {
            14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    private static final int[] SHIFTS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    public static byte[][] generateSubKeys(String hexKey) {
        byte[] key = hexStringToBinary(hexKey);
        return generateSubKeys(key);
    }

    public static byte[][] generateSubKeys(byte[] key) {
        if (key.length != 64) {
            throw new IllegalArgumentException("Length of key must be 64 currently is " + key.length);
        }

        byte[] permutedKey = permute(key, PC1);
        byte[] left = new byte[28];
        byte[] right = new byte[28];
        System.arraycopy(permutedKey, 0, left, 0, 28);
        System.arraycopy(permutedKey, 28, right, 0, 28);

        byte[][] subKeys = new byte[16][48];
        for (int i = 0; i < 16; i++) {
            left = leftShift(left, SHIFTS[i]);
            right = leftShift(right, SHIFTS[i]);
            byte[] combined = new byte[56];
            System.arraycopy(left, 0, combined, 0, 28);
            System.arraycopy(right, 0, combined, 28, 28);
            byte[] subKey = new byte[48];
            for (int j = 0; j < 48; j++) {
                subKey[j] = combined[PC2[j] - 1];
            }
            subKeys[i] = subKey;
        }

        return subKeys;
    }

    public static void main(String[] args) {
        byte[][] subKeys = generateSubKeys("AABB09182736CCDD");
        for (int i = 0; i < 16; i++) {
            System.out.println(binaryToHexString(subKeys[i]));
        }
    }
}
