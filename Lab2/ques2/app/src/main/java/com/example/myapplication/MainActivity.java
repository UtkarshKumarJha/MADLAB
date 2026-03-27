package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNum1, etNum2;
    Button btnAdd, btnSub, btnMul, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        // Set Click Listeners
        btnAdd.setOnClickListener(v -> calculateAndNavigate("+"));
        btnSub.setOnClickListener(v -> calculateAndNavigate("-"));
        btnMul.setOnClickListener(v -> calculateAndNavigate("*"));
        btnDiv.setOnClickListener(v -> calculateAndNavigate("/"));
    }

    private void calculateAndNavigate(String operator) {
        String s1 = etNum1.getText().toString();
        String s2 = etNum2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double num1 = Double.parseDouble(s1);
        double num2 = Double.parseDouble(s2);
        double result = 0;

        // Perform Calculation
        switch (operator) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/":
                if (num2 == 0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                    return;
                }
                result = num1 / num2;
                break;
        }

        // Format the string as requested: "Num1 operator num2 = result"
        String finalResult = num1 + " " + operator + " " + num2 + " = " + result;

        // Create Intent to start ResultActivity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("RESULT_DATA", finalResult); // Pass data
        startActivity(intent);
    }
}