package com.example.demo.ciphers.shift;

import com.example.demo.base.IAttackable;
import com.example.demo.base.ICipher;

public class ShiftCipherLogic implements ICipher<Integer>, IAttackable {

    @Override
    public String encrypt(String input, Integer key) {
        StringBuilder result = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                result.append((char) ((character - base + key) % 26 + base));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String input, Integer key) {
        return encrypt(input, 26 - key);
    }

    @Override
    public String attack(String input) {
        StringBuilder result = new StringBuilder();
        result.append("KEY\t\t\tVALUE\n");
        for (int i = 0; i < 26; i++) {
            result.append(String.format("%d\t\t\t%s\n", i, decrypt(input, i)));
        }
        return result.toString();
    }
}
