package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        EditText etTopic = findViewById(R.id.etProjectTopic);
        EditText etGuide = findViewById(R.id.etGuideName);
        Button btnNext = findViewById(R.id.btnNext3);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, DateTimeActivity.class);
            // Pass Page 3 data forward to Page 4 (so Page 4 can pass it to Page 5)
            intent.putExtra("TOPIC", etTopic.getText().toString());
            intent.putExtra("GUIDE", etGuide.getText().toString());
            startActivity(intent);
        });
    }
}