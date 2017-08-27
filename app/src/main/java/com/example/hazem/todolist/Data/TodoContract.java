package com.example.hazem.todolist.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Hazem on 8/26/2017.
 */

public class TodoContract {

    public static final String content="content://";
    public static final String CONTENT_AUTHORITY = "com.example.hazem.todolist";
    public static final String PATH_TODO = "todolist";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final class TodoEntry implements BaseColumns {
        public final static String TABLE_NAME = "todolist";

        public final static Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TODO);
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_TODO_TITLE ="name";

        public final static String COLUMN_TODO_DESC = "description";



    }
}
