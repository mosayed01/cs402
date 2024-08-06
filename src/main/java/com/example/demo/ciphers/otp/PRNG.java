package com.example.demo.ciphers.otp;

import java.math.BigInteger;

public class PRNG {

    int seed;
    int previous;

    public PRNG() {
        this(13);
    }

    public PRNG(int seed) {
        this.seed = seed;
        this.previous = seed;
    }

    public String generateKey(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ((nextRand() + 'a')));
        }
        return sb.toString();
    }

    private int nextRand() {
        previous = new BigInteger(String.valueOf((5 * previous) + seed)).mod(new BigInteger("26")).intValue();
        return previous;
    }

    public static void main(String[] args) {
        PRNG prng = new PRNG(13);
        for (int i = 0; i < 50; i++) {
            System.out.println(prng.generateKey(30));
        }
    }
}
