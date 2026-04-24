package com.example.myapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView tvFinalData = findViewById(R.id.tvFinalData);
        EventDBHelper db = new EventDBHelper(this);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("LOGGED_IN_EMAIL", "");
        String userName = db.getUserName(userEmail);

        StringBuilder summary = new StringBuilder();
        summary.append("Student Details:\n");
        summary.append("Name: ").append(userName).append("\n");
        summary.append("Email: ").append(userEmail).append("\n\n");
        summary.append("Registered Events:\n");
        summary.append("----------------------------\n");

        Cursor cursor = db.getRegisteredEvents(userEmail);
        
        if (cursor.getCount() == 0) {
            summary.append("No events registered yet.");
        } else {
            int count = 1;
            while (cursor.moveToNext()) {
                summary.append(count++).append(". ").append(cursor.getString(0)).append("\n");
                summary.append("   Date: ").append(cursor.getString(1)).append("\n");
                summary.append("   Time: ").append(cursor.getString(2)).append("\n");
                summary.append("   Location: ").append(cursor.getString(3)).append("\n\n");
            }
        }
        cursor.close();

        tvFinalData.setText(summary.toString());
    }
}
