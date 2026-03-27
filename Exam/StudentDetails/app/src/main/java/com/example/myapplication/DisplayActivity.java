package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tvSummary = findViewById(R.id.tvSummary);

        // Retrieve data from Intent
        String name = getIntent().getStringExtra("NAME");
        String gender = getIntent().getStringExtra("GENDER");
        String course = getIntent().getStringExtra("COURSE");
        String hostel = getIntent().getStringExtra("HOSTEL");

        // Display the data
        String summaryText = "Name: " + name + "\n" +
                "Gender: " + gender + "\n" +
                "Course: " + course + "\n" +
                "Hostel Required: " + hostel;

        tvSummary.setText(summaryText);
    }

    // 1. Inflate the Date Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.date_menu, menu);
        return true;
    }

    // 2. Handle Menu Clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_date) {
            // Get current date and format it
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            // Show the date in a Toast
            Toast.makeText(this, "Today's Date: " + currentDate, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}