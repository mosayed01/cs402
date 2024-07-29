package com.example.demo.logic;

public interface ICipher<KEY> {
    String encrypt(String input, KEY key);
    String decrypt(String input, KEY key);
}
