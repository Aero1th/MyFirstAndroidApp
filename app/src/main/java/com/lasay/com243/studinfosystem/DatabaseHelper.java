package com.lasay.com243.studinfosystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentInfo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "Students";
    private static final String COL_STUDENT_NO = "StudentNo";
    private static final String COL_LAST_NAME = "LastName";
    private static final String COL_FIRST_NAME = "FirstName";
    private static final String COL_MIDDLE_NAME = "MiddleName";
    private static final String COL_PROGRAM = "Program";
    private static final String COL_YEAR_LEVEL = "YearLevel";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + TABLE_STUDENTS + " ("
                + COL_STUDENT_NO + " text primary key, "
                + COL_LAST_NAME + " text, "
                + COL_FIRST_NAME + " text, "
                + COL_MIDDLE_NAME + " text, "
                + COL_PROGRAM + " text, "
                + COL_YEAR_LEVEL + " text)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_STUDENTS);
        onCreate(db);
    }

    public boolean insertStudent(String studentNo, String lastName, String firstName,
                                 String middleName, String program, String yearLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NO, studentNo);
        values.put(COL_LAST_NAME, lastName);
        values.put(COL_FIRST_NAME, firstName);
        values.put(COL_MIDDLE_NAME, middleName);
        values.put(COL_PROGRAM, program);
        values.put(COL_YEAR_LEVEL, yearLevel);

        long result = db.insert(TABLE_STUDENTS, null, values);
        return result != -1;
    }

    public String getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_STUDENTS, null);
        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() == 0) {
            cursor.close();
            return "No saved student records yet.";
        }

        while (cursor.moveToNext()) {
            builder.append("Student No: ").append(cursor.getString(0)).append("\n")
                    .append("Name: ").append(cursor.getString(1)).append(", ")
                    .append(cursor.getString(2)).append(" ")
                    .append(cursor.getString(3)).append("\n")
                    .append("Program: ").append(cursor.getString(4)).append("\n")
                    .append("Year Level: ").append(cursor.getString(5)).append("\n")
                    .append("----------------------------\n");
        }
        cursor.close();
        return builder.toString();
    }
}