package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CampusEvents.db";

    private static final String TABLE_USERS = "users";
    private static final String TABLE_EVENTS = "events";

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3); // Version 3 for 2-table schema with string storage
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT UNIQUE, password TEXT, registered_events TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_EVENTS + " (serial_no INTEGER PRIMARY KEY, name TEXT, date TEXT, time TEXT, location TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // --- USER AUTH ---
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name); cv.put("email", email); cv.put("password", password);
        cv.put("registered_events", "");
        return db.insert(TABLE_USERS, null, cv) != -1;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM " + TABLE_USERS + " WHERE email=?", new String[]{email});
        String name = "Unknown";
        if (cursor.moveToFirst()) name = cursor.getString(0);
        cursor.close();
        return name;
    }

    // --- EVENT MANAGEMENT ---
    public boolean eventExists(int serialNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_EVENTS + " WHERE serial_no=?", new String[]{String.valueOf(serialNo)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean insertEvent(int serialNo, String name, String date, String time, String location) {
        if (eventExists(serialNo)) return false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("serial_no", serialNo);
        cv.put("name", name); cv.put("date", date); cv.put("time", time); cv.put("location", location);
        return db.insert(TABLE_EVENTS, null, cv) != -1;
    }

    public boolean updateEvent(int serialNo, String name, String date, String time, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name); cv.put("date", date); cv.put("time", time); cv.put("location", location);
        return db.update(TABLE_EVENTS, cv, "serial_no=?", new String[]{String.valueOf(serialNo)}) > 0;
    }

    public Cursor getAllEvents() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
    }

    // --- REGISTRATIONS (Using comma-separated string) ---
    private String getRegisteredEventsString(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT registered_events FROM " + TABLE_USERS + " WHERE email=?", new String[]{email});
        String events = "";
        if (cursor.moveToFirst()) {
            events = cursor.getString(0);
        }
        cursor.close();
        return events == null ? "" : events;
    }

    public void registerUserForEvent(String userEmail, int eventId) {
        String currentEvents = getRegisteredEventsString(userEmail);
        List<String> eventList = new ArrayList<>(Arrays.asList(currentEvents.split(",")));
        eventList.remove(""); // Clean empty strings
        
        String idStr = String.valueOf(eventId);
        if (!eventList.contains(idStr)) {
            eventList.add(idStr);
            updateUserEvents(userEmail, TextUtils.join(",", eventList));
        }
    }

    public void unregisterUserFromEvent(String userEmail, int eventId) {
        String currentEvents = getRegisteredEventsString(userEmail);
        List<String> eventList = new ArrayList<>(Arrays.asList(currentEvents.split(",")));
        eventList.remove(String.valueOf(eventId));
        updateUserEvents(userEmail, TextUtils.join(",", eventList));
    }

    public boolean isUserRegisteredForEvent(String userEmail, int eventId) {
        String currentEvents = getRegisteredEventsString(userEmail);
        List<String> eventList = Arrays.asList(currentEvents.split(","));
        return eventList.contains(String.valueOf(eventId));
    }

    private void updateUserEvents(String email, String eventsString) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("registered_events", eventsString);
        db.update(TABLE_USERS, cv, "email=?", new String[]{email});
    }

    public Cursor getRegisteredEvents(String userEmail) {
        String currentEvents = getRegisteredEventsString(userEmail);
        if (currentEvents.isEmpty()) {
            // Return empty cursor if no events
            return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE 1=0", null);
        }
        // Use SQL IN clause
        String query = "SELECT name, date, time, location FROM " + TABLE_EVENTS + " WHERE serial_no IN (" + currentEvents + ")";
        return this.getReadableDatabase().rawQuery(query, null);
    }
}
