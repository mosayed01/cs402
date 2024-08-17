package com.example.demo.ciphers.aes.logic;

import com.example.demo.base.ICipher;
import com.example.demo.ciphers.des.logic.HexString;

public class AESCipher implements ICipher<HexString> {


    @Override
    public String encrypt(String input, HexString hexString) {
        HexString[] keys = AESKeyGenerator.generateKeys(hexString);
        System.out.println("Keys: ");
        for (int i = 0; i < keys.length; i++) {
            System.out.println("Key " + i + ": " + keys[i]);
        }
        return encrypt(input, keys);
    }

    @Override
    public String decrypt(String input, HexString hexString) {
        return "";
    }

    private String encrypt(String input, HexString[] keys) {
        // convert input to 4x4 matrix
        int[][] state = convertHexStringToMatrixInByte(input);
        int[][] key0 = convertHexStringToMatrixInByte(keys[0].toString());

        System.out.println("\nState: ");
        printMatrix(state);
        System.out.println("\nKey 0: ");
        printMatrix(key0);

        // key addition layer
        int[][] addedKey = new int[4][4];
        xor(state, key0, addedKey);


        // round from 1-9
        for (int round = 1; round < 10; round++) {
            System.out.println("\nRound " + round);
            // byte substitution
            int[][] substituted = ByteSubstitution.substitute(addedKey);
            System.out.println("\nSubstituted: ");
            printMatrix(substituted);
            // shift rows
            int[][] shifted = ShiftRows.shiftRows(substituted);
            System.out.println("\nShifted: ");
            printMatrix(shifted);
            // mix columns
            int[][] mixed = MixColumns.mixColumns(shifted);
            System.out.println("\nMixed: ");
            printMatrix(mixed);
            // key addition layer
            int[][] key = convertHexStringToMatrixInByte(keys[round].toString());
            xor(mixed, key, addedKey);
            System.out.println("Round " + round + " result: " + convertMatrixInByteToHexString(addedKey));
            System.out.println();
        }

        // last round without mix columns
        System.out.println("\nRound 10");
        // byte substitution
        int[][] substituted = ByteSubstitution.substitute(addedKey);
        System.out.println("\nSubstituted: ");
        printMatrix(substituted);
        // shift rows
        int[][] shifted = ShiftRows.shiftRows(substituted);
        System.out.println("\nShifted: ");
        printMatrix(shifted);
        // key addition layer
        int[][] key = convertHexStringToMatrixInByte(keys[10].toString());
        System.out.println("\nKey 10: ");
        printMatrix(key);
        xor(shifted, key, addedKey);
        System.out.println("Round 10 result: " + convertMatrixInByteToHexString(addedKey));

        return convertMatrixInByteToHexString(addedKey);
    }

    private void xor(int[][] state, int[][] otherState, int[][] result) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = state[i][j] ^ otherState[i][j];
            }
        }
        System.out.println("\nAdded Key: ");
        printMatrix(result);
    }

    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int b : row) {
                System.out.printf("%02X ", b);
            }
            System.out.println();
        }
    }

    private static int[][] convertHexStringToMatrixInByte(String input) {
        int[][] result = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[j][i] = Integer.parseInt(input.substring(i * 8 + j * 2, i * 8 + j * 2 + 2), 16);
            }
        }

        return result;
    }

    private static String convertMatrixInByteToHexString(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.append(String.format("%02X", matrix[j][i]));
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("\n\nTest Encryption: ");
        HexString hexKey = new HexString("5468617473206D79204B756E67204675");
        String inputForEncryption = "54776F204F6E65204E696E652054776F";
        String cipherText = "29C3505F571420F6402299B31A02D73A";
        AESCipher aesCipher = new AESCipher();
        String encrypted = aesCipher.encrypt(inputForEncryption, hexKey);
        System.out.println("Expected: " + cipherText);
        System.out.println("Actual: " + encrypted);
    }
}
