package com.example.demo.ciphers.des.logic;

import java.util.Arrays;

import static com.example.demo.utils.Utils.permute;

public class Expansion {
    private static final int[] EXPANSION_TABLE = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    public static byte[] expand(byte[] right) {
        return permute(right, EXPANSION_TABLE);
    }

    public static void main(String[] args) {
        byte[] right = {
                0, 1, 0, 0, 0, 1, 0, 1,
                0, 1, 0, 0, 0, 1, 0, 1,
                0, 1, 0, 0, 0, 1, 0, 1,
                0, 1, 0, 0, 0, 1, 0, 1
        };
        System.out.println(Arrays.toString(right) + " -> " + right.length);
        byte[] expanded = expand(right);
        System.out.println(Arrays.toString(expanded) + " -> " + expanded.length);
    }
}
