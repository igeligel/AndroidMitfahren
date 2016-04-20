package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// import com.leon.mitfahren.FeedReaderContract;

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
    // Get a reference to the AutoCompleteTextView in the layout
    AutoCompleteTextView editTextVon = (AutoCompleteTextView) findViewById(R.id.editTextVon);
    AutoCompleteTextView editTextNach = (AutoCompleteTextView) findViewById(R.id.editTextNach);

    // Get the string array
    String[] countries = getResources().getStringArray(R.array.cities_array);
    // Create the adapter and set it to the AutoCompleteTextView
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
    editTextVon.setAdapter(adapter);
    editTextNach.setAdapter(adapter);

    dateButton = (Button) findViewById(R.id.buttonDatum);
    timeButton = (Button) findViewById(R.id.buttonZeit);

    date = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateLabel();
        }
    };

    time = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        updateTimeLabel();
        }
    };

    dateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            MainActivity.this,
            date,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
      }
    });
    timeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //new TimePicker
        new TimePickerDialog(
          MainActivity.this,
          time,
          myCalendar.get(Calendar.HOUR_OF_DAY),
          myCalendar.get(Calendar.MINUTE), true).show();
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


  private long saveData(
      int year,
      int month,
      int day,
      int hour,
      int minute,
      String from,
      String to) {
    FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper((Context)this);

    ContentValues content = new ContentValues();
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM, from);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TO, to);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_YEAR, year);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MONTH, month);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DAY, day);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HOUR, hour);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MINUTE, minute);

    long newRowId;
    SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
    newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, content);

    Log.d("Mitfahren", String.valueOf(newRowId));
    return newRowId;
  }

  /**
   * Save the data of the current calendar.
   * @param view Current View.
   */
  public void saveData(View view) {
    EditText vonText = (EditText) findViewById(R.id.editTextVon);
    EditText nachText = (EditText) findViewById(R.id.editTextNach);

    String fromString = vonText.getText().toString();
    String toString = nachText.getText().toString();

    if (fromString != "" && toString != "") {
      int year = myCalendar.get(Calendar.YEAR);
      int month = myCalendar.get(Calendar.MONTH);
      int day = myCalendar.get(Calendar.DAY_OF_MONTH);
      int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
      int minute = myCalendar.get(Calendar.MINUTE);
      saveData(year, month, day, hour, minute, fromString, toString);
      loadData();
    }
  }

  private String loadData() {
    FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper((Context)this);
    SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();

    String[] projection = {
      FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
      FeedReaderContract.FeedEntry.COLUMN_NAME_FROM,
      FeedReaderContract.FeedEntry.COLUMN_NAME_TO,
      FeedReaderContract.FeedEntry.COLUMN_NAME_YEAR,
      FeedReaderContract.FeedEntry.COLUMN_NAME_MONTH,
      FeedReaderContract.FeedEntry.COLUMN_NAME_DAY,
      FeedReaderContract.FeedEntry.COLUMN_NAME_HOUR,
      FeedReaderContract.FeedEntry.COLUMN_NAME_MINUTE
    };
    String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " DESC";

    Cursor cursor = db.query(
        FeedReaderContract.FeedEntry.TABLE_NAME,
        projection,
        null,
        null,
        null,
        null,
        sortOrder);
    cursor.moveToFirst();
    String test = cursor.getString(
        cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DAY)
    );
    return test;
  }


}
