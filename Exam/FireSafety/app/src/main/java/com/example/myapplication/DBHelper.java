package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FireSafety.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Bookings";
    public static final String COL_ID = "ID";
    public static final String COL_DATE = "DATE";
    public static final String COL_ZONE = "ZONE";
    public static final String COL_DEPLOYMENT = "DEPLOYMENT";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " TEXT, " +
                COL_ZONE + " TEXT, " +
                COL_DEPLOYMENT + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CREATE
    public boolean insertBooking(String date, String zone, String deployment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_ZONE, zone);
        contentValues.put(COL_DEPLOYMENT, deployment);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // READ
    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // UPDATE (Example: Update deployment for a specific ID)
    public boolean updateBooking(String id, String deployment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DEPLOYMENT, deployment);
        int result = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{id});
        return result > 0;
    }

    // DELETE
    public boolean deleteBooking(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{id});
        return result > 0;
    }

    // DELETE ALL
    public void deleteAllBookings() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
