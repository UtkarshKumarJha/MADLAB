package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RelativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_layout); // This links your XML

        // Initialize your UI elements (Optional, but good practice)
        EditText etInput = findViewById(R.id.etInput);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Simple logic to show it works
        btnOk.setOnClickListener(v -> {
            Toast.makeText(this, "You typed: " + etInput.getText(), Toast.LENGTH_SHORT).show();
        });

        btnCancel.setOnClickListener(v -> {
            etInput.setText(""); // Clear the text
        });
    }
}