package com.software.fire.sqlitedatabaserecyclerviewtutorial.database;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Brad on 12/27/2016.
 */

public class TodoLoader extends AsyncTaskLoader<Cursor> {

    private static final String TAG = TodoLoader.class.getSimpleName();

    public TodoLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        DbHelper helper = new DbHelper(this.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor todoCursor = db.rawQuery("SELECT * FROM " + DbHelper.TABLE_TODO + " ORDER BY " + DbHelper.KEY_TIME + " ASC", null);
        return todoCursor;
    }
}
