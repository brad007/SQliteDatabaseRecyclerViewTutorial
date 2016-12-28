package com.software.fire.sqlitedatabaserecyclerviewtutorial.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.software.fire.sqlitedatabaserecyclerviewtutorial.database.DbHelper;

/**
 * Created by Brad on 12/27/2016.
 */

public class AddTodoService extends IntentService {
    private static final String TAG = AddTodoService.class.getSimpleName();

    public static final String ACTION_INSERT = TAG + ".INSERT";
    public static final String EXTRA_VALUES = TAG + ".ContentValues";
    private static Context mContext;

    public AddTodoService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (ACTION_INSERT.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performInsert(values);
        }
    }

    public static void insertNewTodo(Context context, ContentValues values){
        Intent intent = new Intent(context, AddTodoService.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }


    private void performInsert(ContentValues values) {
        if (getContentResolver().insert(DbHelper.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted new Todo");
        } else {
            Log.v(TAG, "Error inserting new card");
        }
    }

}
