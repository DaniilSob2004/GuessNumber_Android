package com.example.guessnumber.utils;

import android.widget.TextView;

public class StartGame {
    public static int generateCountAttempts(TextView attemptsTextView) {
        int countAttempts = TestUtils.getRandomInteger(GameConstant.MIN_ATTEMPTS, GameConstant.MAX_ATTEMPTS);

        String mess = " " + countAttempts;
        attemptsTextView.setText(mess);

        return countAttempts;
    }

    public static boolean minusAttempt(TextView attemptsTextView, int countAttempts) {
        if (countAttempts <= 1) {
            return false;
        }

        String mess = " " + (countAttempts - 1);
        attemptsTextView.setText(mess);
        return true;
    }
}
