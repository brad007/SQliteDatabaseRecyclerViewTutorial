package com.software.fire.sqlitedatabaserecyclerviewtutorial.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Brad on 12/27/2016.
 */

public class TodoProvider extends ContentProvider {
    private static final String TAG = TodoProvider.class.getSimpleName();

    private static final int TODOS = 200;
    private static final int TODOS_WITH_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(DbHelper.CONTENT_AUTHORITY,
                DbHelper.TABLE_TODO,
                TODOS);

        sUriMatcher.addURI(
                DbHelper.CONTENT_AUTHORITY,
                DbHelper.TABLE_TODO + "/#",
                TODOS_WITH_ID
        );
    }

    private DbHelper mTodoDBHelper;

    @Override
    public boolean onCreate() {
        mTodoDBHelper = new DbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor returnCursor = mTodoDBHelper.getReadableDatabase().query(
                DbHelper.TABLE_TODO,
                strings, //projection
                s,  //selection
                strings1, //selectionArgs
                null,
                null,
                s1 //sortOrder
        );
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mTodoDBHelper.getWritableDatabase();

        int _id = (int) db.insert(DbHelper.TABLE_TODO, null, contentValues);
        Uri returnUri;
        if (_id > 0) {
            returnUri = DbHelper.buildTodoUri(_id);
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("This provider does not support deletion");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("This provider does not support updates");
    }
}
