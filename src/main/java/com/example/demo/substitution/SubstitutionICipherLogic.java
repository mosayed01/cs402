package com.example.demo.substitution;

import com.example.demo.logic.IAttackable;
import com.example.demo.logic.ICipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubstitutionICipherLogic implements ICipher<Map<Character, Character>>, IAttackable {

    @Override
    public String encrypt(String input, Map<Character, Character> characterCharacterMap) {
        String lowerCaseInput = input.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (char c : lowerCaseInput.toCharArray()) {
            if (characterCharacterMap.containsKey(c)) {
                sb.append(characterCharacterMap.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String input, Map<Character, Character> characterCharacterMap) {
        String lowerCaseInput = input.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (char c : lowerCaseInput.toCharArray()) {
            if (characterCharacterMap.containsValue(c)) {
                for (Map.Entry<Character, Character> entry : characterCharacterMap.entrySet()) {
                    if (entry.getValue().equals(c)) {
                        sb.append(entry.getKey());
                    }
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // region attack

    private static final List<Character> orderedAlphabet = List.of(
            'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l', 'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'
    );


    @Override
    public String attack(String input) {
        String lowerCaseInput = input.toLowerCase();
        return singleLetterAttack(lowerCaseInput);
    }

    private String singleLetterAttack(String input) {
        StringBuilder sb = new StringBuilder();
        List<Character> orderedAlphabetInInput = findMostCommonLetters(input);
        for (char c : input.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int index = orderedAlphabetInInput.indexOf(c);
                if (index != -1) {
                    sb.append(orderedAlphabet.get(index));
                } else {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private List<Character> findMostCommonLetters(String input) {
        int[] letterCount = new int[26];
        for (char c : input.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                letterCount[c - 'a']++;
            }
        }
        List<Character> orderedAlphabetInInput = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            int maxIndex = 0;
            for (int j = 0; j < 26; j++) {
                if (letterCount[j] > letterCount[maxIndex]) {
                    maxIndex = j;
                }
            }
            orderedAlphabetInInput.add((char) (maxIndex + 'a'));
            letterCount[maxIndex] = 0;
        }
        return orderedAlphabetInInput;
    }

    // endregion
}
