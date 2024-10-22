package com.example.guessnumber;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

public class BotActivity extends AppCompatActivity {

    private LinearLayout userInputLinearLayout;
    private LinearLayout botInputLinearLayout;
    private TextView attemptsTextView;
    private TextView guessNumTextView;
    private TextView botNumInputTextView;
    private TextView errorTextView;

    private int countAttempts;
    private int guessNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.bot_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // добавляем обработчик события "назад"
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        // находиим макет для ввода пользователя
        userInputLinearLayout = findViewById(R.id.user_input_linear_layout);

        // находиим макет для ввода бота
        botInputLinearLayout = findViewById(R.id.bot_input_linear_layout);

        // находиим виджет для загаданного числа
        guessNumTextView = findViewById(R.id.user_guess_num_input_text);
        botNumInputTextView = findViewById(R.id.bot_num_input_text_view);

        // находиим виджет для кол-ва попыток
        attemptsTextView = findViewById(R.id.bot_attempt_text_view);

        // находим виджеты для показа ошибки
        errorTextView = findViewById(R.id.error_text_view);
    }

    public void onClickSend(View v) {
        guessNum = TestUtils.convertStrToInt(guessNumTextView.getText().toString());

        // проверка ввода
        if (guessNum < GameConstant.MIN_NUMBER || guessNum > GameConstant.MAX_NUMBER) {
            String mess = "Number must be from " + GameConstant.MIN_NUMBER + " to " + GameConstant.MAX_NUMBER;
            errorTextView.setText(mess);
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        // показываем макет для бота
        userInputLinearLayout.setVisibility(View.GONE);
        botInputLinearLayout.setVisibility(View.VISIBLE);

        countAttempts = StartGame.generateCountAttempts(attemptsTextView);  // кол-во попыток
        startBotGame();  // запуск игры бота
    }


    private void startBotGame() {
        Handler handler = new Handler();
        int delay = 1500;

        for (int i = 0; i < countAttempts; i++) {
            handler.postDelayed(() -> {
                int botGuessNum = TestUtils.getRandomInteger(GameConstant.MIN_NUMBER, GameConstant.MAX_NUMBER);

                if (botGuessNum == guessNum) {
                    handler.removeCallbacksAndMessages(null);  // прервать выполнение оставшихся задач
                    openGameEndActivity("Bot is winner!!!");  // открываем новое окно
                    return;
                }

                // если попытки ещё есть
                if (StartGame.minusAttempt(attemptsTextView, countAttempts)) {
                    countAttempts--;
                    String mess = "The bot number: " + botGuessNum;
                    botNumInputTextView.setText(mess);
                }
                else {
                    handler.removeCallbacksAndMessages(null);  // прервать выполнение оставшихся задач
                    openGameEndActivity("Bot lost...");  // открываем новое окно
                }
            },delay * i);
        }
    }

    private void openGameEndActivity(String message) {
        Intent intent = new Intent(BotActivity.this, GameEndActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
        finish();
    }
}