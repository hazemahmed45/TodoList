package com.example.hazem.todolist.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Hazem on 8/26/2017.
 */

public class TodoProvider extends ContentProvider{
    private TodoSqlHelper todoSqlHelper;
    private UriMatcher uriMatcher;
    private static final int TODO=101;
    private static final int TODO_ID=105;
    @Override
    public boolean onCreate() {
        todoSqlHelper=new TodoSqlHelper(getContext());
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(String.valueOf(TodoContract.CONTENT_AUTHORITY),TodoContract.PATH_TODO,TODO);
        uriMatcher.addURI(String.valueOf(TodoContract.CONTENT_AUTHORITY),TodoContract.PATH_TODO+"/*",TODO_ID);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String orderBy) {
        SQLiteDatabase database=todoSqlHelper.getReadableDatabase();
        int match=uriMatcher.match(uri);
        Cursor cursor=null;
        switch (match)
        {
            case TODO:
            {
                cursor=database.query(TodoContract.TodoEntry.TABLE_NAME,columns,selection,selectionArgs,null,null,orderBy);
                break;
            }
            case TODO_ID:
            {
                selection= TodoContract.TodoEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=database.query(TodoContract.TodoEntry.TABLE_NAME,columns,selection,selectionArgs,null,null,orderBy);
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Select was wrong");
            }
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        int match= uriMatcher.match(uri);
        Uri newUri=null;
        switch (match)
        {
            case TODO:
            {

                newUri=insertTodo(uri,contentValues);
                break;
            }
            default:
            {
                throw new IllegalArgumentException("No Insertion was made");
            }
        }
        return newUri;
    }
    private Uri insertTodo(Uri uri,ContentValues values)
    {
        String name = values.getAsString(TodoContract.TodoEntry.COLUMN_TODO_TITLE);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        SQLiteDatabase database=todoSqlHelper.getWritableDatabase();
        long id=database.insert(TodoContract.TodoEntry.TABLE_NAME,null,values);
        Uri newUri=ContentUris.withAppendedId(uri,id);
        getContext().getContentResolver().notifyChange(uri,null);
        return newUri;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database=todoSqlHelper.getWritableDatabase();

        int match=uriMatcher.match(uri);
        int rowDeleted=0;
        switch (match)
        {
            case TODO:
            {
                rowDeleted=database.delete(TodoContract.TodoEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case TODO_ID:
            {
                selection= TodoContract.TodoEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted=database.delete(TodoContract.TodoEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Something went wrong with deleting");
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowUpdated=0;
        int match=uriMatcher.match(uri);
        switch (match)
        {
            case TODO_ID:
            {
                selection = TodoContract.TodoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowUpdated=updateTodo(uri,contentValues,selection,selectionArgs);
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Something went wrong with updating");
            }
        }
        return rowUpdated;
    }
    private int updateTodo(Uri uri,ContentValues values,String selection,String[] selecionArgs)
    {
        if (values.containsKey(TodoContract.TodoEntry.COLUMN_TODO_TITLE)) {
            String name = values.getAsString(TodoContract.TodoEntry.COLUMN_TODO_TITLE);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        SQLiteDatabase database=todoSqlHelper.getWritableDatabase();
        int rowUpdated=0;
        getContext().getContentResolver().notifyChange(uri,null);
        rowUpdated=database.update(TodoContract.TodoEntry.TABLE_NAME,values,selection,selecionArgs);
        return rowUpdated;
    }
}
