package com.example.guessnumber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guessnumber.utils.GameConstant;
import com.example.guessnumber.utils.StartGame;
import com.example.guessnumber.utils.TestUtils;

public class UserActivity extends AppCompatActivity {

    private final static String TAG = "UserActivity";

    private TextView attemptsTextView;
    private EditText inputUserNum;
    private LinearLayout errorLinearLayout;
    private TextView errorTextView;

    private int countAttempts;
    private int guessNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // обработка нажатия кнопки назад
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        // находиим виджет для кол-ва попыток
        attemptsTextView = findViewById(R.id.count_attempt_text_view);

        // находиим виджет для ввода числа
        inputUserNum = findViewById(R.id.user_num_input_text);

        // находим виджеты для показа ошибки
        errorLinearLayout = findViewById(R.id.error_linear_layout);
        errorTextView = findViewById(R.id.error_text_view);

        guessNumber();
        countAttempts = StartGame.generateCountAttempts(attemptsTextView);
    }

    public void onClickSend(View v) {
        String inputText = inputUserNum.getText().toString();
        int userInputNumber = TestUtils.convertStrToInt(inputText);

        // победа
        if (userInputNumber == guessNum) {
            openGameEndActivity("You are winner!!!");  // открываем новое окно
            return;
        }

        // число больше или меньше
        String errorMess = " " + ((userInputNumber < guessNum) ?
                GameConstant.ERROR_LESS_MESSAGE :
                GameConstant.ERROR_GREATER_MESSAGE);

        // если попытки ещё есть
        if (StartGame.minusAttempt(attemptsTextView, countAttempts)) {
            countAttempts--;
            errorTextView.setText(errorMess);
            errorLinearLayout.setVisibility(View.VISIBLE);
        }
        else {
            openGameEndActivity("You lost...\nThe hidden number was: " + guessNum);  // открываем новое окно
        }
    }

    
    private void guessNumber() {
        guessNum = TestUtils.getRandomInteger(GameConstant.MIN_NUMBER, GameConstant.MAX_NUMBER);
        Log.d(TAG, "GUESS NUMBER: " + guessNum);
    }

    private void openGameEndActivity(String message) {
        Intent intent = new Intent(UserActivity.this, GameEndActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
        finish();
    }
}