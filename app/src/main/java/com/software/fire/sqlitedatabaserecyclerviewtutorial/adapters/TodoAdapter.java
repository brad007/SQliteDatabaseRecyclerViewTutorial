package com.software.fire.sqlitedatabaserecyclerviewtutorial.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.fire.sqlitedatabaserecyclerviewtutorial.R;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.Utils.DateUtils;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.database.DbHelper;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.models.Todo;

/**
 * Created by Brad on 12/27/2016.
 */

public class TodoAdapter extends CardRecyclerAdapter<TodoViewHolder> {


    public TodoAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        TodoViewHolder viewHolder = new TodoViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder viewHolder, Cursor cursor) {

        Todo todo = new Todo();
        todo.setId(cursor.getInt(cursor.getColumnIndex(DbHelper.KEY_ID)));
        todo.setDescription(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_DESCRIPTION)));
        todo.setTime(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_TIME)));

        viewHolder.setDescription(todo.getDescription());
        viewHolder.setTime(DateUtils.getReadableTime(todo.getTime()));
    }

    @Override
    public int getItemCount() {
        if(getCursor() == null){
            return 0;
        }else{
            return getCursor().getCount();
        }
    }

    @Override
    public Todo getItem(int position) {
        return null;
    }
}
