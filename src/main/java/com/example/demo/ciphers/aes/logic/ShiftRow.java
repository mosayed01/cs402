package com.example.demo.ciphers.aes.logic;

public class ShiftRow {
    public static int[][] shiftRows(int[][] state) {
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(state[i], 0, temp[i], 0, 4);
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = temp[i][(j + i) % 4];
            }
        }
        return state;
    }

    public static int[][] reverseShiftRows(int[][] state) {
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(state[i], 0, temp[i], 0, 4);
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][(j + i) % 4] = temp[i][j];
            }
        }
        return state;
    }

    public static void main(String[] args) {
        int[][] state = {
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 1}
        };
        for (int[] row : state) {
            for (int b : row) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
        System.out.println();
        int[][] shifted = shiftRows(state);
        for (int[] row : shifted) {
            for (int b : row) {
                System.out.print(b + " ");
            }
            System.out.println();
        }

        System.out.println();
        int[][] reverseShifted = reverseShiftRows(shifted);
        for (int[] row : reverseShifted) {
            for (int b : row) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }
}
