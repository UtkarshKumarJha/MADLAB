package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnDate, btnBook;
    TextView tvDate;
    ToggleButton tbReady;
    Spinner spinnerZone;
    String selectedDate = "";
    String[] zones = {"Select Zone", "North", "South", "East", "West"};
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setTitle("Fire Safety");

        btnDate = findViewById(R.id.btnDate);
        tvDate = findViewById(R.id.tvDate);
        tbReady = findViewById(R.id.tbReady);
        spinnerZone = findViewById(R.id.spinnerZone);
        btnBook = findViewById(R.id.btnBook);

        // 1. Date Picker with Constraints
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> {
                selectedDate = day + "/" + (month + 1) + "/" + year;
                tvDate.setText(selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

            // Min Date: Today
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            // Max Date: 20 Days from today
            long twentyDaysInMillis = 20L * 24 * 60 * 60 * 1000;
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + twentyDaysInMillis);

            dialog.show();
        });

        // 2. Toggle Button enables/disables Book Slot
        tbReady.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnBook.setEnabled(isChecked); // Automatically toggles true/false
        });

        // 3. Spinner Logic with Popup Alert
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(adapter);

        spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Ignore the "Select Zone" hint
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Confirm Zone")
                            .setMessage("Selected zone is " + zones[position] + "?")
                            .setPositiveButton("Yes", null) // Do nothing, keep selection
                            .setNegativeButton("No", (dialog, which) -> {
                                spinnerZone.setSelection(0); // Reset to "Select Zone"
                            })
                            .show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 4. Book Slot Button
        btnBook.setOnClickListener(v -> {
            String zone = spinnerZone.getSelectedItem().toString();
            if (selectedDate.isEmpty() || spinnerZone.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select Date and Zone", Toast.LENGTH_SHORT).show();
                return;
            }

            // Deployment logic
            String deploymentText = "";
            switch (zone) {
                case "North": deploymentText = "4 men are deployed"; break;
                case "South": deploymentText = "3 men are deployed"; break;
                case "East":  deploymentText = "2 men are deployed"; break;
                case "West":  deploymentText = "4 women are deployed"; break;
            }

            // CREATE operation
            boolean isInserted = dbHelper.insertBooking(selectedDate, zone, deploymentText);
            if (isInserted) {
                Toast.makeText(this, "Booking saved to Database", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                intent.putExtra("DATE", selectedDate);
                intent.putExtra("ZONE", zone);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Data insertion failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- OPTIONS MENU LOGIC ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fire_safety, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            tvDate.setText("No date selected");
            selectedDate = "";
            tbReady.setChecked(false);
            spinnerZone.setSelection(0);
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_info) {
            new AlertDialog.Builder(this)
                    .setTitle("Station Info")
                    .setMessage("North - 4 men\nSouth - 3 men\nEast - 2 men\nWest - 4 women")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        } else if (id == R.id.action_view_all) {
            // READ operation
            viewAllBookings();
            return true;
        } else if (id == R.id.action_delete_all) {
            // DELETE operation (bulk)
            new AlertDialog.Builder(this)
                    .setTitle("Clear History")
                    .setMessage("Are you sure you want to delete all bookings?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteAllBookings();
                        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewAllBookings() {
        Cursor res = dbHelper.getAllBookings();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("ID: ").append(res.getString(0)).append("\n");
            buffer.append("Date: ").append(res.getString(1)).append("\n");
            buffer.append("Zone: ").append(res.getString(2)).append("\n");
            buffer.append("Status: ").append(res.getString(3)).append("\n\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("All Bookings History")
                .setMessage(buffer.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
