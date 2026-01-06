package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu extends AppCompatActivity {

    Button Numerical_calculator,
            BMI_Calculator,
            small_big,
            pass_generator,
            two_nd_degree_equation,
            Quiz,
            Flash_Light,
            DataBase_sqlite;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_menu);
        Numerical_calculator = findViewById(R.id.Numerical_calculator);
        BMI_Calculator = findViewById(R.id.BMI_Calculator);
        small_big = findViewById(R.id.small_big);
        pass_generator = findViewById(R.id.pass_generator);
        two_nd_degree_equation = findViewById(R.id.two_nd_degree_equation);
        Quiz = findViewById(R.id.Quiz);
        Flash_Light = findViewById(R.id.flashlight);
        DataBase_sqlite = findViewById(R.id.DB);

        Numerical_calculator.setOnClickListener(v -> {
            Intent Intent_Numerical_calculator = new Intent(Menu.this, Numerical_calculator.class);
            startActivity(Intent_Numerical_calculator);
        });

        BMI_Calculator.setOnClickListener(v -> {
            Intent Intent_BMI_Calculator = new Intent(Menu.this, BMI_Calculator.class);
            startActivity(Intent_BMI_Calculator);
        });

        small_big.setOnClickListener(v -> {
            Intent Intent_small_big = new Intent(Menu.this, small_big.class);
            startActivity(Intent_small_big);
        });

        pass_generator.setOnClickListener(v -> {
            Intent Intent_pass_generator = new Intent(Menu.this, pass_generator.class);
            startActivity(Intent_pass_generator);
        });

        two_nd_degree_equation.setOnClickListener(v -> {
            Intent Intent_two_nd_degree_equation = new Intent(Menu.this, two_nd_degree_equation.class);
            startActivity(Intent_two_nd_degree_equation);
        });

        Quiz.setOnClickListener(v -> {
            Intent Intent_Quiz = new Intent(Menu.this, Quiz.class);
            startActivity(Intent_Quiz);
        });

        Flash_Light.setOnClickListener(v -> {
            Intent Intent_Flash_Light = new Intent(Menu.this, flashlight.class);
            startActivity(Intent_Flash_Light);
        });

        DataBase_sqlite.setOnClickListener(v -> {
            Intent Intent_DataBase_sqlite = new Intent(Menu.this, DataBase_sqlite.class);
            startActivity(Intent_DataBase_sqlite);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
