package com.example.guessnumber;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guessnumber.utils.GameConstant;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rules_activity);
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

        setValuesForRules();  // установка значения для правил игры
    }

    public void onBackClick(View v) {
        finish();
    }

    private void setValuesForRules() {
        // установка мин. и макс. значений для попыток и числа

        TextView numberRule = findViewById(R.id.number_rule_text_view);
        String textNumber = "from " + GameConstant.MIN_NUMBER + " to " + GameConstant.MAX_NUMBER + ".\n";
        numberRule.setText(textNumber);

        TextView attemptRule = findViewById(R.id.attempt_rule_text_view);
        String textAttempt = "from " + GameConstant.MIN_ATTEMPTS + " to " + GameConstant.MAX_ATTEMPTS + ".\n";
        attemptRule.setText(textAttempt);
    }
}