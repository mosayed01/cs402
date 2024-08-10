package com.example.demo.ciphers.des.logic.utils;


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


    public static String convertStringToHex64Bit(String input, boolean isCropped) {
        String newString = input;
        if (isCropped && input.length() > 8) {
            newString = input.substring(0, 8);
        }

        return convertStringToHex64Bit(newString);
    }

    public static String convertStringToHex64Bit(String input) {
        if (input.length() > 8) {
            throw new IllegalArgumentException("Input string must be 8 bytes or less.");
        }

        String paddedInput = String.format("%-8s", input);

        StringBuilder hexString = new StringBuilder();
        for (char ch : paddedInput.toCharArray()) {
            hexString.append(String.format("%02x", (int) ch));
        }

        return hexString.toString();
    }


    public static String convertHexToString64Bit(String hex) {
        if (hex.length() != 16) {
            throw new IllegalArgumentException("Hex string must be 16 characters long.");
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    public static void main(String[] args) {
        String originalString = "Hello";
        String hexString = convertStringToHex64Bit(originalString);
        System.out.println("64-bit Hex String: " + hexString);

        String convertedString = convertHexToString64Bit(hexString);
        System.out.println("Converted String: " + convertedString);

        // Assertion
        if (originalString.equals(convertedString.trim())) {
            System.out.println("Test Passed: The original string and the converted string match.");
        } else {
            System.out.println("Test Failed: The original string and the converted string do not match.");
        }
    }

}
