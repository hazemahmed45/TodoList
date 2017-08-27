package com.example.hazem.todolist.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hazem on 8/26/2017.
 */

public class TodoSqlHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="TODO.db";
    public TodoSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + TodoContract.TodoEntry.TABLE_NAME + " ("
                + TodoContract.TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TodoContract.TodoEntry.COLUMN_TODO_TITLE + " TEXT NOT NULL, "
                + TodoContract.TodoEntry.COLUMN_TODO_DESC + " TEXT); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
