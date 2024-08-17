package com.example.demo.ciphers.aes.logic;


import static com.example.demo.ciphers.aes.logic.GaloisFieldMultiply.multiply;

public class MixColumn {

    private static final int[][] MIX_COLUMN_MATRIX = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}
    };

    private static final int[][] REVERSE_MIX_COLUMN_MATRIX = {
            {0x0e, 0x0b, 0x0d, 0x09},
            {0x09, 0x0e, 0x0b, 0x0d},
            {0x0d, 0x09, 0x0e, 0x0b},
            {0x0b, 0x0d, 0x09, 0x0e}
    };

    public static int[][] mixColumns(int[][] state) {
        return multiplyMatrix(state, MIX_COLUMN_MATRIX);
    }

    public static int[][] reverseMixColumns(int[][] state) {
        return multiplyMatrix(state, REVERSE_MIX_COLUMN_MATRIX);
    }

    private static int[][] multiplyMatrix(int[][] state, int[][] matrix) {
        int[][] mixed = new int[4][4];
        for (int i = 0; i < 4; i++) {
            mixed[0][i] = multiply(matrix[0][0], state[0][i]) ^
                    multiply(matrix[0][1], state[1][i]) ^
                    multiply(matrix[0][2], state[2][i]) ^
                    multiply(matrix[0][3], state[3][i]);
            mixed[1][i] = multiply(matrix[1][0], state[0][i]) ^
                    multiply(matrix[1][1], state[1][i]) ^
                    multiply(matrix[1][2], state[2][i]) ^
                    multiply(matrix[1][3], state[3][i]);
            mixed[2][i] = multiply(matrix[2][0], state[0][i]) ^
                    multiply(matrix[2][1], state[1][i]) ^
                    multiply(matrix[2][2], state[2][i]) ^
                    multiply(matrix[2][3], state[3][i]);
            mixed[3][i] = multiply(matrix[3][0], state[0][i]) ^
                    multiply(matrix[3][1], state[1][i]) ^
                    multiply(matrix[3][2], state[2][i]) ^
                    multiply(matrix[3][3], state[3][i]);
        }
        return mixed;
    }

    public static void main(String[] args) {
        int[][] state = {
                {0x32, 0x88, 0x31, 0xe0},
                {0x43, 0x5a, 0x31, 0x37},
                {0xf6, 0x30, 0x98, 0x07},
                {0xa8, 0x8d, 0xa2, 0x34}
        };
        for (int[] row : state) {
            for (int b : row) {
                System.out.print(String.format("%02X", b) + " ");
            }
            System.out.println();
        }
        System.out.println();
        int[][] mixed = mixColumns(state);
        for (int[] row : mixed) {
            for (int b : row) {
                System.out.print(String.format("%02X", b) + " ");
            }
            System.out.println();
        }

        System.out.println();
        int[][] reverseMixed = reverseMixColumns(mixed);
        for (int[] row : reverseMixed) {
            for (int b : row) {
                System.out.print(String.format("%02X", b) + " ");
            }
            System.out.println();
        }
    }
}
