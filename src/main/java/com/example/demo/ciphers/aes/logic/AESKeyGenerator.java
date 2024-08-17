package com.example.demo.ciphers.aes.logic;

import com.example.demo.ciphers.des.logic.HexString;

import java.util.Arrays;

import static com.example.demo.ciphers.aes.logic.ByteSubstitution.substitute;
import static com.example.demo.utils.Utils.xor;

public class AESKeyGenerator {

    private static final int[] RCON = {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36
    };

    public static HexString[] generateKeys(HexString hexKey) {
        if (hexKey.toString().length() != 32) {
            throw new IllegalArgumentException("Key must be 128 bits long");
        }

        int[] bytes = hexKey.toBytes();
        int[][] words = new int[44][4];
        for (int i = 0; i < 4; i++) {
            words[i][0] = bytes[i * 4];
            words[i][1] = bytes[i * 4 + 1];
            words[i][2] = bytes[i * 4 + 2];
            words[i][3] = bytes[i * 4 + 3];
        }

        for (int i = 4; i < 44; i++) {
            if (i % 4 == 0) {
                int[] rotated = new int[4];
                rotated[0] = words[i - 1][1];
                rotated[1] = words[i - 1][2];
                rotated[2] = words[i - 1][3];
                rotated[3] = words[i - 1][0];

                int[] substituted = new int[4];
                for (int j = 0; j < 4; j++) {
                    substituted[j] = substitute(rotated[j]);
                }

                int[] rcon = new int[4];
                rcon[0] = RCON[i / 4 - 1];
                rcon[1] = 0;
                rcon[2] = 0;
                rcon[3] = 0;

                words[i] = xor(xor(words[i - 4], substituted), rcon);
            } else {
                words[i] = xor(words[i - 4], words[i - 1]);
            }
        }

        String[] keys = new String[11];
        for (int i = 0; i < 11; i++) {
            StringBuilder key = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                key.append(String.format("%02X", words[i * 4 + j][0]));
                key.append(String.format("%02X", words[i * 4 + j][1]));
                key.append(String.format("%02X", words[i * 4 + j][2]));
                key.append(String.format("%02X", words[i * 4 + j][3]));
            }
            keys[i] = key.toString();
        }
        return Arrays.stream(keys).map(HexString::new).toArray(HexString[]::new);
    }


    public static void main(String[] args) {
        HexString hexKey = new HexString("5468617473206D79204B756E67204675");
        HexString[] keys = generateKeys(hexKey);
        for (HexString key : keys) {
            System.out.println(key);
        }
    }

}
