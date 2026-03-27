package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLinear = findViewById(R.id.btnLinear);
        Button btnRelative = findViewById(R.id.btnRelative);

        // Click to open Linear Activity
        btnLinear.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LinearActivity.class);
            startActivity(intent);
        });

        // Click to open Relative Activity
        btnRelative.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RelativeActivity.class);
            startActivity(intent);
        });
    }
}