package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_TODO = "todos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_URGENT = "urgent";
    private static final String TAG = "TodoDatabaseHelper";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_TODO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEXT + " TEXT NOT NULL, " +
                    COLUMN_URGENT + " INTEGER DEFAULT 0)";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema changes here
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void addTodo(String text, boolean urgent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        values.put(COLUMN_URGENT, urgent ? 1 : 0);
        long newRowId = db.insert(TABLE_TODO, null, values);
        Log.d(TAG, "New row ID: " + newRowId);
    }

    public Cursor getAllTodos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_TODO, null, null, null, null, null, null);
    }

    @SuppressWarnings("unused")
    public void deleteTodo(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        Log.d(TAG, "Rows deleted: " + rowsDeleted);
    }
}
