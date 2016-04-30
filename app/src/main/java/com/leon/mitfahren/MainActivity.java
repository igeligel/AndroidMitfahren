package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.leon.mitfahren.FeedReaderContract.FeedEntry;


public class MainActivity extends AppCompatActivity {
  // UI Components.
  Button dateButton;
  Button timeButton;
  Button searchButton;
  ExpandableListView expListView;
  AutoCompleteTextView editTextVon;
  AutoCompleteTextView editTextNach;
  RelativeLayout searchField;

  DatePickerDialog.OnDateSetListener datePickerDialogOnSetListener;
  TimePickerDialog.OnTimeSetListener timePickerDialogOnSetListener;

  Calendar myCalendar = Calendar.getInstance();

  // Searchmode.
  boolean searchMode = false;


  ExpandableListAdapter listAdapter;
  List<String> listDataHeader;
  HashMap<String, List<String>> listDataChild;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set current View.
    setContentView(R.layout.search);

    // Get UI Elements.
    searchField = (RelativeLayout) findViewById(R.id.searchField);
    editTextVon = (AutoCompleteTextView) findViewById(R.id.searchTextVon);
    editTextNach = (AutoCompleteTextView) findViewById(R.id.searchTextNach);
    dateButton = (Button) findViewById(R.id.buttonDatum);
    timeButton = (Button) findViewById(R.id.buttonZeit);
    searchButton = (Button) findViewById(R.id.buttonSuchen);
    expListView = (ExpandableListView) findViewById(R.id.lvExp);

    // Create List of our AutoCompleteTextView's.
    ArrayList<AutoCompleteTextView> autoCompleteTextViews = new ArrayList<>();
    autoCompleteTextViews.add(editTextVon);
    autoCompleteTextViews.add(editTextNach);

    //region Initiate UI Elements.

    // Initiate autocomplete with this list.
    initiateAutoComplete(autoCompleteTextViews);
    // Initiate DatePickerDialog and TimePickerDialog.
    initiateDatePickerDialog();
    initiateTimePickerDialog();
    // Initiate Buttons.
    initiateDateButtonBehaviour();
    initiateTimeButtonBehaviour();
    initiateSearchButtonBehaviour();

    //endregion
  }

  //region Menu Implementation.
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
  //endregion

  /**
   * Method to initiate the autocomplete edit text elements.
   */
  private void initiateAutoComplete(ArrayList<AutoCompleteTextView> autoCompleteTextViews) {
    // Get the string array of cities.
    String[] cities = getResources().getStringArray(R.array.cities_array);
    // Create the adapter and set it to the AutoCompleteTextView's.
    ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
    for (AutoCompleteTextView autoCompleteTextView: autoCompleteTextViews) {
      autoCompleteTextView.setAdapter(autoCompleteAdapter);
    }
  }

  private void initiateDatePickerDialog() {
    datePickerDialogOnSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateButtonByCalendar();
      }
    };
  }

  private void initiateTimePickerDialog() {
    timePickerDialogOnSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        updateTimeButtonByCalendar();
      }
    };
  }

  private void initiateDateButtonBehaviour() {
    dateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new DatePickerDialog(
          MainActivity.this,
          datePickerDialogOnSetListener,
          myCalendar.get(Calendar.YEAR),
          myCalendar.get(Calendar.MONTH),
          myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });
  }

  private void initiateTimeButtonBehaviour() {
    timeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new TimePickerDialog(
          MainActivity.this,
          timePickerDialogOnSetListener,
          myCalendar.get(Calendar.HOUR_OF_DAY),
          myCalendar.get(Calendar.MINUTE), true).show();
      }
    });
  }

  private void initiateSearchButtonBehaviour() {
    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get the button parameters which will be changed.
        RelativeLayout.LayoutParams buttonLayoutParams = (RelativeLayout.LayoutParams) searchButton.getLayoutParams();

        // Check for search mode.
        if (searchMode == true) {
          // We need to change the layout and delete the list from entries.
          searchField.setVisibility(View.VISIBLE);
          buttonLayoutParams.addRule(RelativeLayout.BELOW, R.id.searchField);
          searchButton.setLayoutParams(buttonLayoutParams);
          clearExpandableListView();
          searchMode = false;
        } else {
          // Load the list of entries and set the search field invisible.
          testLoad();
          searchField.setVisibility(View.INVISIBLE);
          buttonLayoutParams.addRule(RelativeLayout.BELOW, R.id.dummyLayout);
          searchButton.setLayoutParams(buttonLayoutParams);
          searchMode = true;
        }
      }
    });
  }

  private void updateDateButtonByCalendar() {
    String myFormat = "dd-MM-yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    dateButton.setText(sdf.format(myCalendar.getTime()));
  }

  private void updateTimeButtonByCalendar() {
    String myFormat = "H:mm";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    timeButton.setText(sdf.format(myCalendar.getTime()));
  }

  private ArrayList<JSONObject> loadData(String startPoint, String endPoint, long departureTime) {
    FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper((Context) this);
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

    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + 21600);
    String selection = selectionFrom + " and " + selectionTo + " and " + selectionDeparture;

    Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection,
      selection, null, null, null, sortOrder);

    ArrayList<JSONObject> possibleDrives = new ArrayList<>();
    while (cursor.moveToNext()) {
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

        possibleDrives.add(jObjectData);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return possibleDrives;
  }

  //Sample
  public void testLoad() {
    Log.d("mitfahren", "Started");
    AutoCompleteTextView searchTextVon = (AutoCompleteTextView) findViewById(R.id.searchTextVon);
    AutoCompleteTextView searchTextTo = (AutoCompleteTextView) findViewById(R.id.searchTextNach);

    String from = searchTextVon.getText().toString();
    String to = searchTextTo.getText().toString();
    long timestamp = GetTimestampByCalendar(myCalendar);


    if (from != "" && to != "") {
      ArrayList<JSONObject> possibleResultList = loadData(from, to, timestamp);
      Log.d("mitfahren", "results: " + possibleResultList.size());

      listDataHeader = new ArrayList<>();
      listDataChild = new HashMap<>();
      for (JSONObject json : possibleResultList) {
        try {
          String Id = json.getString(FeedEntry.COLUMN_NAME_ENTRY_ID);
          String JSONfrom = json.getString(FeedEntry.COLUMN_NAME_FROM);
          String JSONto = json.getString(FeedEntry.COLUMN_NAME_TO);
          long JSONdeparture = json.getLong(FeedEntry.COLUMN_NAME_DEPARTURE);
          long JSONarrival = json.getLong(FeedEntry.COLUMN_NAME_ARRIVAL);

          String JSONdescription = json.getString(FeedEntry.COLUMN_NAME_DESCRIPTION);

          Log.d("mitfahren", JSONfrom + " to " + JSONto + ". Arriving at: " + JSONarrival + ". Departure: " + JSONdeparture + ". Description: " + JSONdescription);
          Date departureDate = new Date(JSONdeparture * 1000);
          Date arrivalDate = new Date(JSONarrival * 1000);

          String header = Id;
          List<String> informations = new ArrayList<>();
          informations.add(JSONfrom);
          informations.add(JSONto);
          DateFormat df = new SimpleDateFormat("H:mm");

          informations.add(df.format(departureDate));
          informations.add(df.format(arrivalDate));
          informations.add(JSONdescription);

          listDataHeader.add(header);
          listDataChild.put(header, informations);


        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
      // setting list adapter
      expListView.setAdapter(listAdapter);

    } else {
      Log.d("Else: from", from);
      Log.d("Else: to", to);
    }

  }

  private void clearExpandableListView() {
    listDataHeader.clear();
    listDataChild.clear();
    listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
    expListView.setAdapter(listAdapter);
  }

  private long GetTimestampByCalendar(Calendar calendar) {
    return calendar.getTimeInMillis() / 1000;
  }
}
