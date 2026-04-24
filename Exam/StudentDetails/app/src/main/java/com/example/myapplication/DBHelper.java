package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "students";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_GENDER = "gender";
    public static final String COL_COURSE = "course";
    public static final String COL_HOSTEL = "hostel";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_COURSE + " TEXT, " +
                COL_HOSTEL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CREATE - returns the row ID of the newly inserted row, or -1 if an error occurred
    public long insertStudent(String name, String gender, String course, String hostel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_GENDER, gender);
        values.put(COL_COURSE, course);
        values.put(COL_HOSTEL, hostel);

        return db.insert(TABLE_NAME, null, values);
    }

    // READ
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // UPDATE
    public boolean updateStudent(long id, String name, String gender, String course, String hostel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_GENDER, gender);
        values.put(COL_COURSE, course);
        values.put(COL_HOSTEL, hostel);

        int result = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // DELETE
    public boolean deleteStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
