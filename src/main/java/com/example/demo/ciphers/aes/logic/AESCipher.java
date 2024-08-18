package com.example.demo.ciphers.aes.logic;

import com.example.demo.base.ICipher;
import com.example.demo.ciphers.des.logic.HexString;

import static com.example.demo.utils.Utils.convertFromHexToString;
import static com.example.demo.utils.Utils.convertFromStringToHex;

public class AESCipher implements ICipher<String> {


    // region decryption
    @Override
    public String decrypt(String cipher, String hexString) {
        System.out.println("cipher: " + cipher);
        if (cipher.length() % 32 != 0) {
            throw new IllegalArgumentException("Invalid cipher length");
        }

        int blockCount = Math.ceilDiv(cipher.length(), 32);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < blockCount; i++) {
            String block = cipher.substring(i * 32, (i + 1) * 32);
            result.append(convertFromHexToString(decryptBlock(block, hexString)));
        }
        return result.toString().replaceAll("\0", "");
    }

    private static String decryptBlock(String cipher, String keyString) {
        HexString hexString = convertFromStringToHex(keyString);
        HexString[] keys = AESKeyGenerator.generateKeys(hexString);

        return decrypt(cipher, keys);
    }

    private static String decrypt(String cipher, HexString[] keys) {
        // convert input to 4x4 matrix
        int[][] state = convertHexStringToMatrixInByte(cipher);
        int[][] lastKey = convertHexStringToMatrixInByte(keys[keys.length - 1].toString());

        // first round
        // key addition
        int[][] addedMatrix = new int[4][4];
        xor(state, lastKey, addedMatrix);
        // inverse shift row
        int[][] reverseShifted = ShiftRows.reverseShiftRows(addedMatrix);
        // inverse byte substitution
        int[][] reverseSubstituted = ByteSubstitution.reverseSubstitute(reverseShifted);

        // round from 9-1
        for (int i = 9; i >= 1; i--) {
            // key addition
            int[][] key = convertHexStringToMatrixInByte(keys[i].toString());
            xor(reverseSubstituted, key, addedMatrix);
            // inverse mix columns
            int[][] reverseMixed = MixColumns.reverseMixColumns(addedMatrix);
            // inverse shift row
            reverseShifted = ShiftRows.reverseShiftRows(reverseMixed);
            // inverse byte substitution
            reverseSubstituted = ByteSubstitution.reverseSubstitute(reverseShifted);
        }


        // key addition with k0
        int[][] key0 = convertHexStringToMatrixInByte(keys[0].toString());
        xor(reverseSubstituted, key0, addedMatrix);

        return convertMatrixInByteToHexString(addedMatrix);
    }

    // endregion

    // region encryption
    @Override
    public String encrypt(String input, String keyString) {
        if (input.length() % 16 != 0) {
            input = input + "\0".repeat(16 - input.length() % 16);
        }

        int blockCount = Math.ceilDiv(input.length(), 16);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < blockCount; i++) {
            String block = input.substring(i * 16, Math.min((i + 1) * 16, input.length()));
            result.append(encryptBlock(block, keyString));
        }
        return result.toString();
    }

    private String encryptBlock(String input, String keyString) {
        HexString hexString = convertFromStringToHex(keyString);
        HexString inputInHex = convertFromStringToHex(input);
        HexString[] keys = AESKeyGenerator.generateKeys(hexString);
        return encrypt(inputInHex.toString(), keys);
    }

    private String encrypt(String input, HexString[] keys) {
        // convert input to 4x4 matrix
        int[][] state = convertHexStringToMatrixInByte(input);
        int[][] key0 = convertHexStringToMatrixInByte(keys[0].toString());

        // key addition layer
        int[][] addedKey = new int[4][4];
        xor(state, key0, addedKey);


        // round from 1-9
        for (int round = 1; round < 10; round++) {
            // byte substitution
            int[][] substituted = ByteSubstitution.substitute(addedKey);
            // shift rows
            int[][] shifted = ShiftRows.shiftRows(substituted);
            // mix columns
            int[][] mixed = MixColumns.mixColumns(shifted);
            // key addition layer
            int[][] key = convertHexStringToMatrixInByte(keys[round].toString());
            xor(mixed, key, addedKey);
        }

        // last round without mix columns
        // byte substitution
        int[][] substituted = ByteSubstitution.substitute(addedKey);
        // shift rows
        int[][] shifted = ShiftRows.shiftRows(substituted);
        // key addition layer
        int[][] key = convertHexStringToMatrixInByte(keys[10].toString());
        xor(shifted, key, addedKey);

        return convertMatrixInByteToHexString(addedKey);
    }
    // endregion

    // region utils
    private static void xor(int[][] state, int[][] otherState, int[][] result) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = state[i][j] ^ otherState[i][j];
            }
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
    // endregion

    public static void main(String[] args) {
        String key = "Thats my Kung Fu";
        String inputForEncryption = "Two One Nine Two";
        AESCipher aesCipher = new AESCipher();
        String encrypted = aesCipher.encrypt(inputForEncryption, key);
        String decrypted = aesCipher.decrypt(encrypted, key);

        System.out.println("\n\nTest Encryption: ");
        System.out.println("Key: " + key);
        System.out.println("Input: " + inputForEncryption);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
