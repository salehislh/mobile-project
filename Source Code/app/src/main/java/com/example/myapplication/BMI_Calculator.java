package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BMI_Calculator extends AppCompatActivity {

    private EditText getWeight, getHeight;
    private TextView bmiResult, bmiStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        getWeight = findViewById(R.id.GetWeight);
        getHeight = findViewById(R.id.GetHeight);
        bmiResult = findViewById(R.id.Bmi_Result);
        bmiStatus = findViewById(R.id.Bmi_Status);
        Button calculateBmi = findViewById(R.id.Calculate_Bmi);

        calculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void calculateBMI() {
        String weightStr = getWeight.getText().toString();
        String heightStr = getHeight.getText().toString();

        // Check if fields are empty
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Complete both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);

        // Validate weight and height
        if (weight < 20) {
            Toast.makeText(this, "Please enter your actual Weight", Toast.LENGTH_SHORT).show();
            return;
        }

        if (height > 300) {
            Toast.makeText(this, "Please enter your actual Height", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate BMI
        double bmi = weight / Math.pow(height / 100, 2); // Height in meters

        // Display BMI result
        bmiResult.setText(String.format("%.2f", bmi));

        // Determine BMI status
        if (bmi < 18.5) {
            bmiStatus.setText("شما دچار کمبود وزن هستید");
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            bmiStatus.setText("وزن شما نرمال است");
        } else if (bmi >= 25 && bmi <= 29.9) {
            bmiStatus.setText("شما دچار اضافه وزن هستید");
        } else if (bmi >= 30 && bmi <= 34.9) {
            bmiStatus.setText("شما دچار چاقی هستید");
        } else {
            bmiStatus.setText("شما دچار چاقی شدید هستید");
        }
    }
}