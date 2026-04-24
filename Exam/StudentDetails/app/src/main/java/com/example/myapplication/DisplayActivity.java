package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisplayActivity extends AppCompatActivity {

    DBHelper dbHelper;
    long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        dbHelper = new DBHelper(this);

        TextView tvSummary = findViewById(R.id.tvSummary);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnViewAll = findViewById(R.id.btnViewAll);

        // Retrieve data from Intent
        studentId = getIntent().getLongExtra("ID", -1);
        String name = getIntent().getStringExtra("NAME");
        String gender = getIntent().getStringExtra("GENDER");
        String course = getIntent().getStringExtra("COURSE");
        String hostel = getIntent().getStringExtra("HOSTEL");

        // Display the data
        displaySummary(name, gender, course, hostel);

        // Update Operation (Example: Updating the name)
        btnUpdate.setOnClickListener(v -> {
            String updatedName = name + " (Updated)";
            boolean isUpdated = dbHelper.updateStudent(studentId, updatedName, gender, course, hostel);
            if (isUpdated) {
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                displaySummary(updatedName, gender, course, hostel);
            }
        });

        // Delete Operation
        btnDelete.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteStudent(studentId);
            if (isDeleted) {
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                finish(); // Close activity after deletion
            }
        });

        // Read All Operation
        btnViewAll.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getAllStudents();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                builder.append("ID: ").append(cursor.getString(0)).append("\n");
                builder.append("Name: ").append(cursor.getString(1)).append("\n");
                builder.append("Gender: ").append(cursor.getString(2)).append("\n");
                builder.append("Course: ").append(cursor.getString(3)).append("\n");
                builder.append("Hostel: ").append(cursor.getString(4)).append("\n\n");
            }
            cursor.close();

            // Show records in a dialog
            new AlertDialog.Builder(this)
                    .setTitle("All Student Records")
                    .setMessage(builder.toString())
                    .setPositiveButton("OK", null)
                    .show();
        });
    }

    private void displaySummary(String name, String gender, String course, String hostel) {
        TextView tvSummary = findViewById(R.id.tvSummary);
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
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Toast.makeText(this, "Today's Date: " + currentDate, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
