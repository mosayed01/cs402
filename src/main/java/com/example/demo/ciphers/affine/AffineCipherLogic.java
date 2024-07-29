package com.example.demo.ciphers.affine;

import com.example.demo.base.IAttackable;
import com.example.demo.base.ICipher;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class AffineCipherLogic implements ICipher<Pair<Integer, Integer>>, IAttackable {

    @Override
    public String encrypt(String input, Pair<Integer, Integer> key) {
        int a = key.getKey();
        int b = key.getValue();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isLetter(ch)) {
                char offset = Character.isUpperCase(ch) ? 'A' : 'a';
                result.append((char) ((a * (ch - offset) + b) % 26 + offset));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String input, Pair<Integer, Integer> key) {
        int a = key.getKey();
        int b = key.getValue();
        int aInverse = modInverse(a);
        if (aInverse == -1) {
            throw new IllegalArgumentException("Inverse does not exist for given key a");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isLetter(ch)) {
                char offset = Character.isUpperCase(ch) ? 'A' : 'a';
                result.append((char) (aInverse * (ch - offset - b + 26) % 26 + offset));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    @Override
    public String attack(String input) {
        List<Integer> validAValues = getValidAValues();
        StringBuilder allDecryptions = new StringBuilder();

        for (int a : validAValues) {
            for (int b = 0; b < 26; b++) {
                Pair<Integer, Integer> key = new Pair<>(a, b);
                String decryptedText = decrypt(input, key);
                allDecryptions.append("a=").append(a)
                        .append(", b=").append(b)
                        .append(": ").append(decryptedText)
                        .append("\n");
            }
        }

        return allDecryptions.toString();
    }

    private List<Integer> getValidAValues() {
        List<Integer> validAValues = new ArrayList<>();
        for (int a = 1; a < 26; a++) {
            if (gcd(a, 26) == 1) {
                validAValues.add(a);
            }
        }
        return validAValues;
    }

    private int modInverse(int a) {
        a = a % 26;
        for (int x = 1; x < 26; x++) {
            if ((a * x) % 26 == 1) {
                return x;
            }
        }
        return -1;
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
