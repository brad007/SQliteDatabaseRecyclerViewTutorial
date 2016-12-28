package com.software.fire.sqlitedatabaserecyclerviewtutorial.database;

import android.content.ContentUris;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Brad on 12/27/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    //All static variables
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "todoDatabase";

    //Table name
    public static final String TABLE_TODO = "todo";

    //Table Column Names
    public static final String KEY_ID = "_id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TIME = "time";

    public static final String CONTENT_AUTHORITY = "com.software.fire.sqlitedatabaserecyclerviewtutorial";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_TODO)
            .build();

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_DESCRIPTION + " TEXT, "
                + KEY_TIME + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);

        //Create tables again
        onCreate(sqLiteDatabase);
    }

    public static Uri buildTodoUri(int id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
