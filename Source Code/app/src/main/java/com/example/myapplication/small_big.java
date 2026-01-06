package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class small_big extends AppCompatActivity {

    private EditText userNumbersEditText;
    private EditText inputUserNumbersEditText;
    private TextView listShowTextView;
    private TextView lowNumHighNumShowTextView;
    private final ArrayList<Double> numbers = new ArrayList<>();
    private int maxNumbers;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_small_big);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userNumbersEditText = findViewById(R.id.UserNumbers);
        inputUserNumbersEditText = findViewById(R.id.InputUserNumbers);
        listShowTextView = findViewById(R.id.ListShow);
        lowNumHighNumShowTextView = findViewById(R.id.LowNum_HighNum_Show);

        Button nextNumberButton = findViewById(R.id.NextNumber);
        Button calculateButton = findViewById(R.id.Calculate_low_high);

        decimalFormat = new DecimalFormat("#.#");

        nextNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maxInput = userNumbersEditText.getText().toString();
                if (!maxInput.isEmpty()) {
                    try {
                        maxNumbers = Integer.parseInt(maxInput);
                        if (maxNumbers <= 0) {
                            Toast.makeText(small_big.this, "لطفاً یک عدد مثبت وارد کنید.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(small_big.this, "لطفاً یک عدد معتبر وارد کنید.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(small_big.this, "چند عدد میخواهید وارد کنید؟", Toast.LENGTH_SHORT).show();
                    return;
                }

                String input = inputUserNumbersEditText.getText().toString();
                if (!input.isEmpty()) {
                    try {
                        if (numbers.size() < maxNumbers) {
                            double number = Double.parseDouble(input);
                            numbers.add(number);
                            updateListShow();
                        } else {
                            Toast.makeText(small_big.this, "سقف وارد کردن عدد " + maxNumbers + " است.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(small_big.this, "لطفاً یک عدد معتبر وارد کنید.", Toast.LENGTH_SHORT).show();
                    }
                }
                inputUserNumbersEditText.setText("");
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!numbers.isEmpty()) {
                    double largest = getLargest(numbers);
                    double smallest = getSmallest(numbers);
                    lowNumHighNumShowTextView.setText("بزرگترین عدد: " + formatNumber(largest) + " \n کوچکترین عدد: " + formatNumber(smallest));
                    updateListShow();
                } else {
                    Toast.makeText(small_big.this, "لطفاً ابتدا اعداد را وارد کنید.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateListShow() {
        StringBuilder listStringBuilder = new StringBuilder();
        for (Double number : numbers) {
            listStringBuilder.append(formatNumber(number)).append(" , ");
        }
        listShowTextView.setText(listStringBuilder.toString());
    }

    private String formatNumber(double number) {
        if (number == Math.floor(number)) {
            return String.valueOf((int) number);
        } else {
            return decimalFormat.format(number);
        }
    }

    private double getLargest(ArrayList<Double> numbers) {
        double largest = numbers.get(0);
        for (double number : numbers) {
            if (number > largest) {
                largest = number;
            }
        }
        return largest;
    }

    private double getSmallest(ArrayList<Double> numbers) {
        double smallest = numbers.get(0);
        for (double number : numbers) {
            if (number < smallest) {
                smallest = number;
            }
        }
        return smallest;
    }
}