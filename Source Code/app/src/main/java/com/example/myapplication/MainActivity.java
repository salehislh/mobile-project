package com.example.myapplication;

import static com.example.myapplication.R.layout.activity_main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText Name;
    EditText Code;
    EditText Pass;
    Button MainSubmit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(activity_main);
        Name = findViewById(R.id.GetName);
        Code = findViewById(R.id.GetCode);
        Pass = findViewById(R.id.GetPass);
        MainSubmit = findViewById(R.id.MainSubmit);

        MainSubmit.setOnClickListener(v -> {
            String password = Pass.getText().toString();
            if (!Name.getText().toString().isEmpty() && !Code.getText().toString().isEmpty() && !password.isEmpty()) {
                if (isPasswordValid(password)) {
                    Intent myintent = new Intent(MainActivity.this, Menu.class);
                    startActivity(myintent);
                } else {
                    Toast.makeText(MainActivity.this, "Minimums are not met", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "The information is not complete", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isPasswordValid(String password) {
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());

        int digitCount = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        String specialCharacters = "!%#@}{_-$#@^&*()<>?|/";
        boolean hasSpecialChar = false;
        for (char c : specialCharacters.toCharArray()) {
            if (password.contains(String.valueOf(c))) {
                hasSpecialChar = true;
                break;
            }
        }

        return hasUpperCase && hasLowerCase && digitCount >= 4 && hasSpecialChar;
    }
}