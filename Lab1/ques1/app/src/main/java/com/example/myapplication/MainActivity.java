package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding the default TextView by ID
        TextView txtDefault = findViewById(R.id.txtDefault);

        // Demonstrating how to change properties dynamically in Java
        txtDefault.setText("1. Modified by Java Code");
        txtDefault.setTextColor(Color.parseColor("#008000")); // Dark Green
        txtDefault.setTextSize(22);
        txtDefault.setTypeface(Typeface.DEFAULT_BOLD);
    }
}