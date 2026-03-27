package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView tvSummary = findViewById(R.id.tvFinalSummary);

        // Retrieve data passed from Page 4 (which includes Page 3 data)
        String topic = getIntent().getStringExtra("TOPIC");
        String guide = getIntent().getStringExtra("GUIDE");
        String date = getIntent().getStringExtra("DATE");
        String time = getIntent().getStringExtra("TIME");

        String summaryText = "--- Page 3 Contents ---\n" +
                "Project: " + topic + "\n" +
                "Guide: " + guide + "\n\n" +
                "--- Page 4 Contents ---\n" +
                "Scheduled Date: " + date + "\n" +
                "Scheduled Time: " + time;

        tvSummary.setText(summaryText);
    }
}