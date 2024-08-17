package com.example.demo.ciphers.des.logic;

import com.example.demo.base.ICipher;
import com.example.demo.utils.Utils;

import java.util.ArrayList;

import static com.example.demo.utils.Utils.*;

public class DESCipher implements ICipher<HexString> {

    private static final int[] INITIAL_PERMUTATION_TABLE = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    private static final int[] FINAL_PERMUTATION_TABLE = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };


    @Override
    public String encrypt(String generalInput, HexString hexString) {
        byte[] key = hexString.toBinaryByteArray();
        byte[][] subKeys = KeySchedule.generateSubKeys(key);

        ArrayList<String> blocks = new ArrayList<>();
        int length = Math.ceilDiv(generalInput.length(), 8);
        for (int i = 0; i < length; i++) {
            int start = i * 8;
            int endIfNotMultipleOf8 = (i + 1) * 8;
            if (endIfNotMultipleOf8 > generalInput.length()) {
                endIfNotMultipleOf8 = generalInput.length();
            }
            String block = generalInput.substring(start, endIfNotMultipleOf8);
            blocks.add(Utils.convertStringToHex64Bit(block));
        }


        StringBuilder result = new StringBuilder();
        for (String block : blocks) {
            result.append(encrypt(block, subKeys));
        }
        return result.toString();
    }

    @Override
    public String decrypt(String input, HexString hexString) {
        byte[] key = hexString.toBinaryByteArray();
        byte[][] subKeys = KeySchedule.generateSubKeys(key);

        byte[][] reversedSubKeys = new byte[16][48];
        for (int i = 0; i < 16; i++) {
            reversedSubKeys[i] = subKeys[15 - i];
        }

        ArrayList<String> blocks = new ArrayList<>();
        int length = Math.ceilDiv(input.length(), 16);

        for (int i = 0; i < length; i++) {
            int start = i * 16;
            int endIfNotMultipleOf16 = (i + 1) * 16;
            if (endIfNotMultipleOf16 > input.length()) {
                endIfNotMultipleOf16 = input.length();
            }
            String block = input.substring(start, endIfNotMultipleOf16);
            blocks.add(block);
        }

        StringBuilder result = new StringBuilder();
        for (String block : blocks) {
            result.append(Utils.convertHexToString64Bit(encrypt(block, reversedSubKeys)));
        }
        return result.toString();
    }

    private String encrypt(String input, byte[][] subKeys) {
        byte[] binaryInput = new HexString(input).toBinaryByteArray();
        byte[] permutedInput = permute(binaryInput, INITIAL_PERMUTATION_TABLE);

        byte[] left = new byte[32];
        byte[] right = new byte[32];
        System.arraycopy(permutedInput, 0, left, 0, 32);
        System.arraycopy(permutedInput, 32, right, 0, 32);

        for (int i = 0; i < 16; i++) {
            byte[] expandedRight = Expansion.expand(right); // 48 bits
            byte[] xored = xor(expandedRight, subKeys[i]);
            byte[] substituted = SBoxes.substituteAndPermute(xored);
            byte[] newRight = xor(left, substituted);
            left = right;
            right = newRight;
        }

        byte[] combined = new byte[64];
        System.arraycopy(right, 0, combined, 0, 32);
        System.arraycopy(left, 0, combined, 32, 32);

        byte[] cipherText = permute(combined, FINAL_PERMUTATION_TABLE);
        return binaryToHexString(cipherText);
    }


    public static void main(String[] args) {
        DESCipher desCipher = new DESCipher();
        String generalKey = "MohammedSayedMohammedSayed";
        String key = Utils.convertStringToHex64Bit(generalKey, true);
        String plainText = "MohammedSayedMohammedSayed";
        String cipherText = desCipher.encrypt(plainText, new HexString(key));
        System.out.println('\n' + cipherText);
        System.out.println(desCipher.decrypt(cipherText, new HexString(key)));
    }
}