package com.software.fire.sqlitedatabaserecyclerviewtutorial.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.software.fire.sqlitedatabaserecyclerviewtutorial.R;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.adapters.TodoAdapter;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.database.DbHelper;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.models.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<Todo> todos = new ArrayList<>();
    private RecyclerView todoRecyclerView;

    private static final int URL_LOADER = 0;

    private static final String[] TODO_COLUMNS = {
            DbHelper.KEY_ID,
            DbHelper.KEY_DESCRIPTION,
            DbHelper.KEY_TIME
    };

    private TodoAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(URL_LOADER, null, this);

        todoRecyclerView = (RecyclerView) findViewById(R.id.todo_recycler);
        todoRecyclerView.setHasFixedSize(true);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        setupAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupAdapter() {
        DbHelper db = new DbHelper(MainActivity.this);

        Cursor cursor = db.getWritableDatabase()
                .query(
                        DbHelper.TABLE_TODO,
                        TODO_COLUMNS,
                        null, null, null, null, null
                );
        cursor.moveToFirst();
        mAdapter = new TodoAdapter(MainActivity.this, cursor);
        todoRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                return new CursorLoader(
                        MainActivity.this,
                        DbHelper.CONTENT_URI,
                        TODO_COLUMNS,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

}
