package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SportsActivity extends AppCompatActivity {

    // Array of sports data
    String[] sports = {"Cricket", "Football", "Tennis", "Basketball", "Badminton", "Hockey", "Volleyball", "Table Tennis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        ListView listView = findViewById(R.id.listViewSports);

        // Create an adapter to bridge the data (sports array) to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Default Android list item layout
                sports
        );

        listView.setAdapter(adapter);

        // Handle item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected sport text
                String selectedSport = sports[position];

                // Display the Toast message
                Toast.makeText(SportsActivity.this, "Selected Sport: " + selectedSport, Toast.LENGTH_SHORT).show();
            }
        });
    }
}