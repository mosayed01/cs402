package com.example.demo.ciphers.des.logic.utils;

import com.example.demo.ciphers.des.logic.KeySchedule;
import com.example.demo.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyScheduleTest {

    @Test
    void generateSubKeys() {
        // given a key
        String hexKey = "AABB09182736CCDD";
        // when generating sub keys
        byte[][] subKeys = KeySchedule.generateSubKeys(hexKey);
        // then return the correct sub keys
        String[] trueResults = {
                "194CD072DE8C",
                "4568581ABCCE",
                "06EDA4ACF5B5",
                "DA2D032B6EE3",
                "69A629FEC913",
                "C1948E87475E",
                "708AD2DDB3C0",
                "34F822F0C66D",
                "84BB4473DCCC",
                "02765708B5BF",
                "6D5560AF7CA5",
                "C2C1E96A4BF3",
                "99C31397C91F",
                "251B8BC717D0",
                "3330C5D9A36D",
                "181C5D75C66D",
        };

        for (int i = 0; i < 16; i++) {
            assertEquals(trueResults[i], Utils.binaryToHexString(subKeys[i]));
        }

    }
}