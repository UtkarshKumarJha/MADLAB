package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMovie, spinnerTheatre;
    Button btnDate, btnTime, btnBook, btnReset;
    ToggleButton toggleTicketType;
    TextView tvError;

    String selectedDate = "";
    String selectedTime = "";
    int selectedHour = 0; // To track 12:00 PM logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI
        spinnerMovie = findViewById(R.id.spinnerMovie);
        spinnerTheatre = findViewById(R.id.spinnerTheatre);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnBook = findViewById(R.id.btnBook);
        btnReset = findViewById(R.id.btnReset);
        toggleTicketType = findViewById(R.id.toggleTicketType);
        tvError = findViewById(R.id.tvError);

        // 1. Populate Spinners
        String[] movies = {"Avengers: Endgame", "Inception", "The Dark Knight", "Interstellar"};
        String[] theatres = {"PVR Cinemas", "INOX", "Cinepolis", "IMAX"};

        ArrayAdapter<String> movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, movies);
        movieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovie.setAdapter(movieAdapter);

        ArrayAdapter<String> theatreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatres);
        theatreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheatre.setAdapter(theatreAdapter);

        // Set Defaults
        resetForm();

        // 2. Date Picker
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        btnDate.setText(selectedDate);
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        // 3. Time Picker
        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        selectedHour = hourOfDay; // Store hour for validation
                        String amPm = (hourOfDay >= 12) ? "PM" : "AM";
                        selectedTime = String.format("%02d:%02d %s", hourOfDay, minute, amPm);
                        btnTime.setText(selectedTime);
                        validateBooking(); // Check validity whenever time changes
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
            timePicker.show();
        });

        // 4. Toggle Button Listener
        toggleTicketType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            validateBooking(); // Check validity whenever ticket type changes
        });

        // 5. Book Now Button
        btnBook.setOnClickListener(v -> {
            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select Date and Time", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate Random Seat Availability
            int seats = new Random().nextInt(50) + 1;

            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            intent.putExtra("MOVIE", spinnerMovie.getSelectedItem().toString());
            intent.putExtra("THEATRE", spinnerTheatre.getSelectedItem().toString());
            intent.putExtra("DATE", selectedDate);
            intent.putExtra("TIME", selectedTime);
            intent.putExtra("TYPE", toggleTicketType.isChecked() ? "Premium" : "Standard");
            intent.putExtra("SEATS", seats);
            startActivity(intent);
        });

        // 6. Reset Button
        btnReset.setOnClickListener(v -> resetForm());
    }

    // Helper to validate the 12:00 PM rule
    private void validateBooking() {
        boolean isPremium = toggleTicketType.isChecked();

        // Rule: If Premium, time must be >= 12 (12:00 PM or later)
        if (isPremium && selectedHour < 12) {
            btnBook.setEnabled(false);
            btnBook.setAlpha(0.5f); // Dim the button
            tvError.setVisibility(View.VISIBLE);
        } else {
            btnBook.setEnabled(true);
            btnBook.setAlpha(1.0f);
            tvError.setVisibility(View.GONE);
        }
    }

    // Helper to Reset Form
    private void resetForm() {
        spinnerMovie.setSelection(0);
        spinnerTheatre.setSelection(0);
        toggleTicketType.setChecked(false); // Default to Standard

        Calendar c = Calendar.getInstance();
        selectedDate = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
        btnDate.setText("Date: " + selectedDate);

        selectedHour = c.get(Calendar.HOUR_OF_DAY);
        selectedTime = String.format("%02d:%02d", selectedHour, c.get(Calendar.MINUTE));
        btnTime.setText("Time: " + selectedTime);

        validateBooking();
    }
}