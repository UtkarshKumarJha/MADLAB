package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView txtResult = findViewById(R.id.txtResult);
        Button btnBack = findViewById(R.id.btnBack);

        // 1. Get the Intent that started this activity
        String resultString = getIntent().getStringExtra("RESULT_DATA");

        // 2. Set the text
        if (resultString != null) {
            txtResult.setText(resultString);
        }

        // 3. Handle Back Button
        btnBack.setOnClickListener(v -> {
            // finish() closes the current activity and returns to the previous one on the stack
            finish();
        });
    }
}