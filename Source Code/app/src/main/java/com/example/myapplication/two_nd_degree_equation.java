package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class two_nd_degree_equation extends AppCompatActivity {

    private EditText parameterAInput, parameterBInput, parameterCInput;
    private TextView mainResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_nd_degree_equation);

        parameterAInput = findViewById(R.id.GetParameter_A);
        parameterBInput = findViewById(R.id.GetParameter_B);
        parameterCInput = findViewById(R.id.GetParameter_C);
        mainResultText = findViewById(R.id.MainResult);
        Button calculateButton = findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRoots();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void calculateRoots() {
        String aStr = parameterAInput.getText().toString();
        String bStr = parameterBInput.getText().toString();
        String cStr = parameterCInput.getText().toString();

        if (aStr.isEmpty() || bStr.isEmpty() || cStr.isEmpty()) {
            Toast.makeText(this, "Complete all fields!!َ", Toast.LENGTH_SHORT).show();
            return;
        }

        float Parameter_A = Float.parseFloat(aStr);
        float Parameter_B = Float.parseFloat(bStr);
        float Parameter_C = Float.parseFloat(cStr);

        // Validate that Parameter_A is greater than 0
        if (Parameter_A <= 0) {
            Toast.makeText(this, "Variable A must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        float delta = Parameter_B * Parameter_B - 4 * Parameter_A * Parameter_C;

        if (delta < 0) {
            mainResultText.setText("این تابع ریشه ندارد");
            return;
        } else if (delta == 0) {
            float MainResult1 = -Parameter_B / (2 * Parameter_A);
            mainResultText.setText(String.format("ریشه تابع برابر با %.2f میباشد", MainResult1));
            return;
        } else {
            double MainResult1 = (-Parameter_B + Math.sqrt(delta)) / (2 * Parameter_A);
            double MainResult2 = (-Parameter_B - Math.sqrt(delta)) / (2 * Parameter_A);
            mainResultText.setText(String.format("تابع دارای دو ریشه میباشد: %.2f, %.2f", MainResult1, MainResult2));
        }
    }
}