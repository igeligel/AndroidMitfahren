package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// import com.leon.mitfahren.FeedReaderContract;

public class MainActivity extends AppCompatActivity {
  Button dateButton;
  Button timeButton;
  Button searchButton;

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

    searchButton = (Button) findViewById(R.id.buttonSuchen);

    // Get the string array
    String[] countries = getResources().getStringArray(R.array.cities_array);
    // Create the adapter and set it to the AutoCompleteTextView
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
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

    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("Mitfahren Suche:", loadData());
      }
    });
  }

  private void updateDateLabel() {
    String myFormat = "MM/dd/yy"; // In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    dateButton.setText(sdf.format(myCalendar.getTime()));
  }

  private void updateTimeLabel() {
    String myFormat = "H:mm";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    timeButton.setText(sdf.format(myCalendar.getTime()));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.game_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.search:
        return true;
      case R.id.create:
        Intent intent = new Intent(this, Create.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  // TODO: Gib dir das mal als objekt zurück und probier ne liste einzufügen :P + Bedingungen für die Suche.
  private String loadData() {
    FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper((Context)this);
    SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();

    String[] projection = {
      FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
      FeedReaderContract.FeedEntry.COLUMN_NAME_FROM,
      FeedReaderContract.FeedEntry.COLUMN_NAME_TO,
      FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL,
      FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE,
      FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION
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
    cursor.moveToLast();

    JSONObject jObjectData = new JSONObject();
    try {
      jObjectData.put(
        FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
        cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID)));
      jObjectData.put(
        FeedReaderContract.FeedEntry.COLUMN_NAME_FROM,
        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM)));
      jObjectData.put(
        FeedReaderContract.FeedEntry.COLUMN_NAME_TO,
        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TO)));
      jObjectData.put(
        FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL,
        cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL)));
      jObjectData.put(
        FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE,
        cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE)));
      jObjectData.put(
        FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION)));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    Log.d("Last Entry", jObjectData.toString());
    return jObjectData.toString();
  }
}
