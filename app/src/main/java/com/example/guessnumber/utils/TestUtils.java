package com.example.guessnumber.utils;

import java.util.Random;

public class TestUtils {
    private static final Random RAND = new Random();
    private static final int MIN_INT = 1;
    private static final int MAX_INT = 100_000_000;

    public static int convertStrToInt(final String strNum) {
        int number;
        try {
            number = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            number = 0;
        }
        return number;
    }

    public static int getRandomInteger() {
        return getRandomInteger(MIN_INT, MAX_INT);
    }

    public static int getRandomInteger(int min) {
        return getRandomInteger(min, MAX_INT);
    }

    public static int getRandomInteger(final int min, final int max) {
        return RAND.nextInt((max - min) + 1) + min;
    }
}
