package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView tvDetails = findViewById(R.id.tvMovieDetails);
        Button btnHome = findViewById(R.id.btnHome);

        // Retrieve Data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String movie = extras.getString("MOVIE");
            String theatre = extras.getString("THEATRE");
            String date = extras.getString("DATE");
            String time = extras.getString("TIME");
            String type = extras.getString("TYPE");
            int seats = extras.getInt("SEATS");

            String summary = "Movie: " + movie + "\n\n" +
                    "Theatre: " + theatre + "\n\n" +
                    "Show: " + date + " at " + time + "\n\n" +
                    "Ticket: " + type + "\n\n" +
                    "Available Seats: " + seats;

            tvDetails.setText(summary);
        }

        btnHome.setOnClickListener(v -> finish());
    }
}