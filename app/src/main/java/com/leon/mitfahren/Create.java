package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Create extends AppCompatActivity {
  Calendar abfahrtCalendar = Calendar.getInstance();
  Calendar ankunftCalendar = Calendar.getInstance();
  Button abfahrtButton;
  Button ankunftButton;

  Button erstellButton;

  DatePickerDialog.OnDateSetListener abfahrtdpdDateSetListener;
  TimePickerDialog.OnTimeSetListener abfahrttpSetListener;

  DatePickerDialog.OnDateSetListener ankunftdpdDateSetListener;
  TimePickerDialog.OnTimeSetListener ankunfttpSetListener;

  AutoCompleteTextView editTextVon;
  AutoCompleteTextView editTextNach;
  EditText editTextBeschreibung;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create);

    editTextVon = (AutoCompleteTextView) findViewById(R.id.editTextVon);
    editTextNach = (AutoCompleteTextView) findViewById(R.id.editTextNach);

    String[] cities = getResources().getStringArray(R.array.cities_array);
    ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cities);
    editTextVon.setAdapter(adapter);
    editTextNach.setAdapter(adapter);


    abfahrtButton = (Button) findViewById(R.id.buttonAbfahrt);
    ankunftButton = (Button) findViewById(R.id.buttonAnkunft);
    erstellButton = (Button) findViewById(R.id.buttonErstellen);

    // Abfahrt Begin
    abfahrttpSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        abfahrtCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        abfahrtCalendar.set(Calendar.MINUTE, minute);
        updateAbfahrtButton();
      }
    };

    abfahrtdpdDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        abfahrtCalendar.set(Calendar.YEAR, year);
        abfahrtCalendar.set(Calendar.MONTH, monthOfYear);
        abfahrtCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        new TimePickerDialog(
          Create.this,
          abfahrttpSetListener,
          abfahrtCalendar.get(Calendar.HOUR_OF_DAY),
          abfahrtCalendar.get(Calendar.MINUTE), true).show();
      }
    };

    abfahrtButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
          Create.this,
          abfahrtdpdDateSetListener,
          abfahrtCalendar.get(Calendar.YEAR),
          abfahrtCalendar.get(Calendar.MONTH),
          abfahrtCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
      }
    });
    // Abfahrt ende

    // Ankunft Anfang
    ankunfttpSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ankunftCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        ankunftCalendar.set(Calendar.MINUTE, minute);
        updateAnkunftButton();
      }
    };

    ankunftdpdDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ankunftCalendar.set(Calendar.YEAR, year);
        ankunftCalendar.set(Calendar.MONTH, monthOfYear);
        ankunftCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        new TimePickerDialog(
          Create.this,
          ankunfttpSetListener,
          ankunftCalendar.get(Calendar.HOUR_OF_DAY),
          ankunftCalendar.get(Calendar.MINUTE), true).show();
      }
    };

    ankunftButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
          Create.this,
          ankunftdpdDateSetListener,
          ankunftCalendar.get(Calendar.YEAR),
          ankunftCalendar.get(Calendar.MONTH),
          ankunftCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
      }
    });
    // Ankunft ende


    erstellButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        editTextVon = (AutoCompleteTextView) findViewById(R.id.editTextVon);
        editTextNach = (AutoCompleteTextView) findViewById(R.id.editTextNach);
        editTextBeschreibung = (EditText) findViewById(R.id.descriptionEditText);
        saveData(GetTimestampByCalendar(ankunftCalendar),
          GetTimestampByCalendar(abfahrtCalendar),
          editTextVon.getText().toString(),
          editTextNach.getText().toString(),
          editTextBeschreibung.getText().toString());
      }
    });
  }

  private void updateAbfahrtButton() {
    String myFormat = "MM/dd/yy H:mm"; // In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    abfahrtButton.setText(sdf.format(abfahrtCalendar.getTime()));
  }

  private void updateAnkunftButton() {
    String myFormat = "MM/dd/yy H:mm"; // In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    ankunftButton.setText(sdf.format(ankunftCalendar.getTime()));
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
      case R.id.create:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private long GetTimestampByCalendar(Calendar calendar) {
    return calendar.getTimeInMillis() / 1000;
  }

  private long saveData(
    long timestampAnkunft,
    long timestampAbfahrt,
    String from,
    String to,
    String description) {
    FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper((Context)this);

    ContentValues content = new ContentValues();
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM, from);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TO, to);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE, timestampAbfahrt);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL, timestampAnkunft);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);

    long newRowId;
    SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
    newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, content);

    Log.d("Neue Mitfahrt mit Id:", String.valueOf(newRowId));
    return newRowId;
  }

  public void saveData(View vw) {

  }
}
