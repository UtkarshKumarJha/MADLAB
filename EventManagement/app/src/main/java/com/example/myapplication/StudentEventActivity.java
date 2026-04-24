package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentEventActivity extends AppCompatActivity {
    EventDBHelper db;
    ArrayList<EventModel> eventList;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_event);

        db = new EventDBHelper(this);
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userEmail = prefs.getString("LOGGED_IN_EMAIL", "");

        ListView listView = findViewById(R.id.listViewEvents);
        eventList = new ArrayList<>();

        loadEvents();

        EventAdapter adapter = new EventAdapter(this, eventList, userEmail, db);
        listView.setAdapter(adapter);

        findViewById(R.id.btnViewSummary).setOnClickListener(v -> {
            startActivity(new Intent(this, SummaryActivity.class));
        });
    }

    private void loadEvents() {
        eventList.clear();
        Cursor cursor = db.getAllEvents();
        while(cursor.moveToNext()) {
            eventList.add(new EventModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
        cursor.close();
    }

    static class EventModel {
        int serialNo;
        String name, date, time, location;
        EventModel(int s, String n, String d, String t, String l) {
            this.serialNo = s; this.name = n; this.date = d; this.time = t; this.location = l;
        }
    }

    class EventAdapter extends BaseAdapter {
        Context context;
        ArrayList<EventModel> events;
        String email;
        EventDBHelper dbHelper;

        EventAdapter(Context c, ArrayList<EventModel> e, String em, EventDBHelper dbh) {
            this.context = c; this.events = e; this.email = em; this.dbHelper = dbh;
        }

        @Override public int getCount() { return events.size(); }
        @Override public Object getItem(int position) { return events.get(position); }
        @Override public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
            }

            EventModel event = events.get(position);
            TextView tvName = convertView.findViewById(R.id.tvEventName);
            TextView tvDetails = convertView.findViewById(R.id.tvEventDetails);
            ToggleButton toggle = convertView.findViewById(R.id.btnToggleRegister);

            tvName.setText(event.name);
            tvDetails.setText(event.date + " | " + event.time + " | " + event.location);

            // Remove listener before setting state to avoid triggering it
            toggle.setOnCheckedChangeListener(null);
            toggle.setChecked(dbHelper.isUserRegisteredForEvent(email, event.serialNo));

            toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    dbHelper.registerUserForEvent(email, event.serialNo);
                } else {
                    dbHelper.unregisterUserFromEvent(email, event.serialNo);
                }
            });

            return convertView;
        }
    }
}
