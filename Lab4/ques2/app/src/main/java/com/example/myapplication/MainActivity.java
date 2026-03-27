package com.example.myapplication;

import static com.example.myapplication.R.*;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnLab4Q2 = findViewById(id.btnSports);
        btnLab4Q2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AndroidVersionsActivity.class));
        });
    }
}