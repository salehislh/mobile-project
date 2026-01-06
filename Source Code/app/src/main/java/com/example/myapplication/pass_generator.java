package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class pass_generator extends AppCompatActivity {

    private Button btnCreate;
    private CheckBox checkBoxABC, checkBoxVAR, checkBox123;
    private EditText passwordResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pass_generator);

        // Initialize views
        btnCreate = findViewById(R.id.btnCreate);
        checkBoxABC = findViewById(R.id.checkBoxABC);
        checkBoxVAR = findViewById(R.id.checkBoxVAR);
        checkBox123 = findViewById(R.id.checkBox123);
        passwordResult = findViewById(R.id.PasswordResult);

        // Set click listener for the create button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePassword();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void generatePassword() {
        StringBuilder password = new StringBuilder();
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_+{}[]";

        String chars = lowerCase;

        if (checkBoxABC.isChecked()) {
            chars += upperCase;
        }

        if (checkBox123.isChecked()) {
            chars += numbers;
        }

        if (checkBoxVAR.isChecked()) {
            chars += specialChars;
        }

        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        passwordResult.setText(password.toString());
    }
}
