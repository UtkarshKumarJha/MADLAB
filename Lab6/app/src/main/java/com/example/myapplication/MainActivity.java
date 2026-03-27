package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    TextView tvPageTitle, tvContentText;
    ImageView ivContentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tvPageTitle = findViewById(R.id.tvPageTitle);
        tvContentText = findViewById(R.id.tvContentText);
        ivContentImage = findViewById(R.id.ivContentImage);

        // Set up the Toolbar as the App Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("XYZ Fitness");
        }
    }

    // Inflate the menu into the App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fitness_menu, menu);
        return true;
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Hide image by default, only show when needed (e.g., for Trainers)
        ivContentImage.setVisibility(View.GONE);

        int id = item.getItemId();

        // Part B: Icon Clicks
        if (id == R.id.action_home) {
            tvPageTitle.setText("Homepage");
            tvContentText.setText("Welcome to XYZ Fitness Center!\n\nAchieve your dream physique with our state-of-the-art equipment and expert guidance.");
            return true;
        }
        else if (id == R.id.action_about) {
            tvPageTitle.setText("About Us");
            tvContentText.setText("XYZ Fitness Center was established in 2020. We believe in holistic health and provide the best environment for our members to thrive.");
            return true;
        }
        else if (id == R.id.action_contact) {
            tvPageTitle.setText("Contact Us");
            tvContentText.setText("Email: support@xyzfitness.com\nPhone: +1 234 567 8900\nAddress: 123 Fitness Street, Muscle City.");
            return true;
        }

        // Part A: Text Menu Clicks
        else if (id == R.id.action_workout) {
            tvPageTitle.setText("Workout Plans");
            tvContentText.setText("1. Weight Loss - High-intensity interval training (HIIT) and cardio.\n\n" +
                    "2. Muscle Gain - Heavy lifting and progressive overload programs.\n\n" +
                    "3. Yoga & Flexibility - Stretching and core strengthening.");
            return true;
        }
        else if (id == R.id.action_trainers) {
            tvPageTitle.setText("Our Trainers");
            ivContentImage.setVisibility(View.VISIBLE); // Show photo for trainers
            ivContentImage.setImageResource(android.R.drawable.ic_menu_camera); // Placeholder for trainer photo
            tvContentText.setText("Trainer: John Doe\nSpecialization: Bodybuilding & Muscle Gain\n\n" +
                    "Trainer: Jane Smith\nSpecialization: Weight Loss & Nutrition\n\n" +
                    "Trainer: Mike Johnson\nSpecialization: Yoga & Rehabilitation");
            return true;
        }
        else if (id == R.id.action_membership) {
            tvPageTitle.setText("Membership Packages");
            tvContentText.setText("1. Basic Plan: $30/month\n(Access to gym equipment only)\n\n" +
                    "2. Premium Plan: $60/month\n(Gym access + Group Classes + Sauna)\n\n" +
                    "3. VIP Plan: $100/month\n(All Premium features + Personal Trainer + Diet Plan)");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}