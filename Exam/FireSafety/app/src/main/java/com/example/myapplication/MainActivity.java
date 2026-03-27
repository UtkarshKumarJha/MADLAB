package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            if (selectedDate.isEmpty() || spinnerZone.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select Date and Zone", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            intent.putExtra("DATE", selectedDate);
            intent.putExtra("ZONE", spinnerZone.getSelectedItem().toString());
            startActivity(intent);
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
        if (item.getItemId() == R.id.action_refresh) {
            // Refresh everything
            tvDate.setText("No date selected");
            selectedDate = "";
            tbReady.setChecked(false);
            spinnerZone.setSelection(0);
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.action_info) {
            // Static Station Info Popup
            new AlertDialog.Builder(this)
                    .setTitle("Station Info")
                    .setMessage("North - 4 men\nSouth - 3 men\nEast - 2 men\nWest - 4 women")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}