package com.software.fire.sqlitedatabaserecyclerviewtutorial.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.software.fire.sqlitedatabaserecyclerviewtutorial.R;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.Utils.DateUtils;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.database.DbHelper;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.models.Todo;
import com.software.fire.sqlitedatabaserecyclerviewtutorial.services.AddTodoService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Todo mTodo;
    private TextView mDateView;
    private TextView mTimeView;
    private Date mDate;
    private Calendar mCalendar = Calendar.getInstance();
    private AutoCompleteTextView mDescriptionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialiseView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialiseView() {
        mDateView = (TextView) findViewById(R.id.date_tv);
        mDateView.setOnClickListener(this);
        mTimeView = (TextView) findViewById(R.id.time_tv);
        mTimeView.setOnClickListener(this);
        mDescriptionView = (AutoCompleteTextView) findViewById(R.id.description_view);
        mDate = new Date();
        mTodo = new Todo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_tv:
                setDate();
                break;
            case R.id.time_tv:
                setTime();
                break;
            case R.id.fab:
                sendTodo();
        }
    }

    private void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMinDate(now);
        datePickerDialog.setOnDateSetListener(this);
        datePickerDialog.show(getFragmentManager(), null);
    }

    private void setTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
        );

        timePickerDialog.setOnTimeSetListener(this);
        timePickerDialog.show(getFragmentManager(), null);


    }

    private void sendTodo() {
        mCalendar.setTime(mDate);
        String description = mDescriptionView.getText().toString();
        long time = mDate.getTime();

        mTodo.setDescription(description);
        mTodo.setTime(String.valueOf(time));

        ContentValues values = new ContentValues();
        values.put(DbHelper.KEY_DESCRIPTION, mTodo.getDescription());
        values.put(DbHelper.KEY_TIME, mTodo.getTime());
        Intent intent = new Intent(AddTodoActivity.this, AddTodoService.class);
        startService(intent);
        AddTodoService.insertNewTodo(AddTodoActivity.this, values);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mDate.setYear(year);
        mDate.setMonth(monthOfYear);
        mDate.setDate(dayOfMonth);
        mDateView.setText(DateUtils.getReadableDate(String.valueOf(mDate.getTime())));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        mDate.setHours(hourOfDay);
        mDate.setMinutes(minute);
        mDate.setSeconds(second);

        mTimeView.setText(hourOfDay + ":" + minute);
    }
}
