package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.leon.data.database.FeedReaderContract;
import com.leon.data.database.FeedReaderDbHelper;
import com.leon.models.Ride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Create extends AppCompatActivity {
  // UI Elements
  Button departureButton;
  Button arrivalButton;
  AutoCompleteTextView editTextDeparture;
  AutoCompleteTextView editTextArrival;
  EditText editTextDescription;
  Button createRideButton;

  // Listener
  DatePickerDialog.OnDateSetListener departureDatePickerDialogDateSetListener;
  TimePickerDialog.OnTimeSetListener departureTimePickerSetListener;

  DatePickerDialog.OnDateSetListener arrivalDatePickerDateSetListener;
  TimePickerDialog.OnTimeSetListener arrivalTimePickerSetListener;

  Calendar departureCalendar = Calendar.getInstance();
  Calendar arrivalCalendar = Calendar.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create);

    editTextDeparture = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewDepartureCity);
    editTextArrival = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewArrivalCity);
    departureButton = (Button) findViewById(R.id.buttonDeparture);
    arrivalButton = (Button) findViewById(R.id.buttonArrival);
    createRideButton = (Button) findViewById(R.id.buttonCreateRide);

    String[] cities = getResources().getStringArray(R.array.cities_array);
    ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cities);
    editTextDeparture.setAdapter(adapter);
    editTextArrival.setAdapter(adapter);

    initiateDepartureUserInterface();
    initiateArrivalUserInterface();
    initiateCreateButton();
  }

  private void initiateDepartureUserInterface() {
    // Abfahrt Begin
    departureTimePickerSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        departureCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        departureCalendar.set(Calendar.MINUTE, minute);
        updateDepartureButton();
      }
    };

    departureDatePickerDialogDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        departureCalendar.set(Calendar.YEAR, year);
        departureCalendar.set(Calendar.MONTH, monthOfYear);
        departureCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        new TimePickerDialog(
          Create.this,
          departureTimePickerSetListener,
          departureCalendar.get(Calendar.HOUR_OF_DAY),
          departureCalendar.get(Calendar.MINUTE), true).show();
      }
    };

    departureButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
          Create.this,
          departureDatePickerDialogDateSetListener,
          departureCalendar.get(Calendar.YEAR),
          departureCalendar.get(Calendar.MONTH),
          departureCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
      }
    });
    // Abfahrt ende
  }

  private void initiateArrivalUserInterface() {
    arrivalTimePickerSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        arrivalCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        arrivalCalendar.set(Calendar.MINUTE, minute);
        updateArrivalButton();
      }
    };

    arrivalDatePickerDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        arrivalCalendar.set(Calendar.YEAR, year);
        arrivalCalendar.set(Calendar.MONTH, monthOfYear);
        arrivalCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        new TimePickerDialog(
          Create.this,
          arrivalTimePickerSetListener,
          arrivalCalendar.get(Calendar.HOUR_OF_DAY),
          arrivalCalendar.get(Calendar.MINUTE), true).show();
      }
    };

    arrivalButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
          Create.this,
          arrivalDatePickerDateSetListener,
          arrivalCalendar.get(Calendar.YEAR),
          arrivalCalendar.get(Calendar.MONTH),
          arrivalCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
      }
    });
  }

  private void initiateCreateButton() {
    createRideButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        editTextDeparture = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewDepartureCity);
        editTextArrival = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewArrivalCity);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        if (editTextDeparture != null && editTextArrival != null && editTextDescription != null) {
          Ride ride = new Ride();
          ride.DepartureTime = GetTimestampByCalendar(departureCalendar);
          ride.ArrivalTime = GetTimestampByCalendar(arrivalCalendar);
          ride.DepartureCity = editTextDeparture.getText().toString();
          ride.ArrivalCity = editTextArrival.getText().toString();
          ride.Description = editTextDescription.getText().toString();
          saveData(ride);
        }
      }
    });
  }

  private void updateDepartureButton() {
    String myFormat = "dd-MM-yy H:mm"; // In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    departureButton.setText(sdf.format(departureCalendar.getTime()));
  }

  private void updateArrivalButton() {
    String myFormat = "dd-MM-yy H:mm"; // In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    arrivalButton.setText(sdf.format(arrivalCalendar.getTime()));
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

  private long saveData(Ride ride) {
    // Check if params are valid.
    if (!createParametersValid(ride))
      return -1;
    FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);
    ContentValues content = new ContentValues();
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM, ride.DepartureCity);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TO, ride.ArrivalCity);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE, ride.DepartureTime);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL, ride.ArrivalTime);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, ride.Description);

    long newRowId;
    SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
    newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, content);
    ToastManager.makeToast("Fahrt mit der Id #" + newRowId + " erstellt.", getApplicationContext());
    return newRowId;
  }

  private boolean createParametersValid(Ride ride) {
    if (ride.DepartureCity.length() == 0) {
      ToastManager.makeToast("Keine Abfahrtsstadt angegeben.", getApplicationContext());
      return false;
    }
    if (ride.ArrivalCity.length() == 0) {
      ToastManager.makeToast("Keine Ankunftsstadt angegeben.", getApplicationContext());
      return false;
    }
    if (ride.DepartureTime < (System.currentTimeMillis()/1000)) {
      ToastManager.makeToast("Abfahrtszeit ist ungültig.", getApplicationContext());
      return false;
    }
    if (ride.ArrivalTime < (System.currentTimeMillis()/1000)) {
      ToastManager.makeToast("Ankunftszeit ist ungültig.", getApplicationContext());
      return false;
    }
    if (ride.Description == "") {
      ToastManager.makeToast("Keine Beschreibung angegeben.", getApplicationContext());
      return false;
    }
    if (ride.ArrivalTime <= ride.DepartureTime) {
      ToastManager.makeToast("Die Ankunftszeit kann nicht vor der Abfahrtszeit liegen.", getApplicationContext());
      return false;
    }
    return true;
  }
}
