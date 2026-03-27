package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerSource, spinnerDest;
    Button btnDate, btnSubmit, btnReset;
    ToggleButton toggleTripType;
    String selectedDate = ""; // To store the chosen date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerDest = findViewById(R.id.spinnerDest);
        btnDate = findViewById(R.id.btnDate);
        toggleTripType = findViewById(R.id.toggleTripType);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);

        // 1. Setup Spinners with Data
        String[] places = {"New York", "London", "Paris", "Tokyo", "Mumbai", "Dubai"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, places);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter);
        spinnerDest.setAdapter(adapter);

        // Set Default Date to Today
        resetDateToToday();

        // 2. Date Picker Logic
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        btnDate.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // 3. Submit Button Logic
        btnSubmit.setOnClickListener(v -> {
            String source = spinnerSource.getSelectedItem().toString();
            String dest = spinnerDest.getSelectedItem().toString();
            String tripType = toggleTripType.isChecked() ? "Round Trip" : "One Way";

            if (source.equals(dest)) {
                Toast.makeText(this, "Source and Destination cannot be same!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Start New Activity
            Intent intent = new Intent(MainActivity.this, TicketDetailsActivity.class);
            intent.putExtra("SOURCE", source);
            intent.putExtra("DEST", dest);
            intent.putExtra("DATE", selectedDate);
            intent.putExtra("TYPE", tripType);
            startActivity(intent);
        });

        // 4. Reset Button Logic
        btnReset.setOnClickListener(v -> {
            spinnerSource.setSelection(0);
            spinnerDest.setSelection(0);
            toggleTripType.setChecked(false); // Reset to "One Way"
            resetDateToToday(); // Reset Date to System Date
            Toast.makeText(this, "Form Reset", Toast.LENGTH_SHORT).show();
        });
    }

    private void resetDateToToday() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1; // Month is 0-indexed
        int day = c.get(Calendar.DAY_OF_MONTH);
        selectedDate = day + "/" + month + "/" + year;
        btnDate.setText("Date: " + selectedDate);
    }
}