package com.software.fire.sqlitedatabaserecyclerviewtutorial.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.software.fire.sqlitedatabaserecyclerviewtutorial.R;

/**
 * Created by Brad on 12/27/2016.
 */

public class TodoViewHolder extends RecyclerView.ViewHolder {

    TextView descriptionText;
    TextView timeText;

    public TodoViewHolder(View itemView) {
        super(itemView);
        descriptionText = (TextView) itemView.findViewById(R.id.description_view);
        timeText = (TextView) itemView.findViewById(R.id.time_view);
    }

    public void setDescription(String description) {
        descriptionText.setText(description);
    }

    public void setTime(String time) {
        timeText.setText(time);
    }
}
