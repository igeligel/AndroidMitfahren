package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.leon.mitfahren.FeedReaderContract.*;

public class MainActivity extends AppCompatActivity {

    Button dateButton;
    Button timeButton;
    Calendar myCalendar = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        dateButton = (Button) findViewById(R.id.buttonDatum);
        timeButton = (Button) findViewById(R.id.buttonZeit);

        date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        time = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDialog = new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                mDialog.show();

            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new TimePicker
                new TimePickerDialog(MainActivity.this, time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void updateDateLabel() {
        String myFormat = "MM/dd/yy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateButton.setText(sdf.format(myCalendar.getTime()));
        // dateButton.setFreezesText(true);
    }

    private void updateTimeLabel() {
        String myFormat = "H:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        timeButton.setText(sdf.format(myCalendar.getTime()));

    }

    public void saveData(View view) {
        EditText vonText = (EditText) findViewById(R.id.editTextVon);
        EditText nachText = (EditText) findViewById(R.id.editTextNach);

        String fromString = vonText.getText().toString();
        String toString = nachText.getText().toString();

        if(fromString != "" && toString != ""){
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);

            saveData(year, month, day, hour, minute, fromString, toString);
            loadData();
        }
    }

    private String loadData(){
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper((Context)this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                FeedEntry.COLUMN_NAME_ENTRY_ID,
                FeedEntry.COLUMN_NAME_FROM,
                FeedEntry.COLUMN_NAME_TO,
                FeedEntry.COLUMN_NAME_YEAR,
                FeedEntry.COLUMN_NAME_MONTH,
                FeedEntry.COLUMN_NAME_DAY,
                FeedEntry.COLUMN_NAME_HOUR,
                FeedEntry.COLUMN_NAME_MINUTE
        };

        String sortOrder =
                FeedEntry.COLUMN_NAME_FROM + " DESC";

        Cursor c = db.query(FeedEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
        c.moveToFirst();
        String test = c.getString(c.getColumnIndex(FeedEntry.COLUMN_NAME_DAY));
        return test;
    }

    private long saveData(int year, int month, int day, int hour, int minute, String from, String to){
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper((Context)this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(FeedEntry.COLUMN_NAME_FROM, from);
        content.put(FeedEntry.COLUMN_NAME_TO, to);
        content.put(FeedEntry.COLUMN_NAME_YEAR, year);
        content.put(FeedEntry.COLUMN_NAME_MONTH, month);
        content.put(FeedEntry.COLUMN_NAME_DAY, day);
        content.put(FeedEntry.COLUMN_NAME_HOUR, hour);
        content.put(FeedEntry.COLUMN_NAME_MINUTE, minute);

        long newRowId;
        newRowId = db.insert(FeedEntry.TABLE_NAME, null, content);

        return newRowId;
    }

}
