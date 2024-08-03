package com.example.demo.ciphers.otp;

import com.example.demo.base.ICipher;

public class OTPCipherLogic implements ICipher<Object> {
    String key;
    private static final PRNG prng = new PRNG();

    @Override
    public String encrypt(String input, Object s) {
        key = prng.generateKey(input.length());

        if (input.length() != key.length()) {
            throw new IllegalArgumentException("Key length must be equal to input length");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                sb.append(input.charAt(i));
                continue;
            }

            char base = Character.isUpperCase(input.charAt(i)) ? 'A' : 'a';
            int a = input.charAt(i) - base;
            int b = key.charAt(i) - 'a';
            int c = (a + b) % 26;
            sb.append((char) (c + base));
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String input, Object s) {
        if (input.length() != key.length()) {
            throw new IllegalArgumentException("Key length must be equal to input length");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                sb.append(input.charAt(i));
                continue;
            }

            char base = Character.isUpperCase(input.charAt(i)) ? 'A' : 'a';
            int a = input.charAt(i) - base;
            int b = key.charAt(i) - 'a';
            int c = (a - b + 26) % 26;
            sb.append((char) (c + base));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        OTPCipherLogic otp = new OTPCipherLogic();
        String input = "Hello World!";
        String encrypted = otp.encrypt(input, null);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + otp.decrypt(encrypted, null));
    }
}
