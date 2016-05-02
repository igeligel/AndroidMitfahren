package com.leon.mitfahren;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.leon.mitfahren.FeedReaderContract.FeedEntry;
import com.leon.models.Ride;
import com.leon.models.SearchType;


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
        for (AutoCompleteTextView autoCompleteTextView : autoCompleteTextViews) {
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
                    if (getSearchType() == SearchType.NoCity) {
                        makeToast("Keine Stadt angegeben");
                        return;
                    }
                    searchEntities();
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

    private String[] getSearchProjection() {
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_FROM,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TO,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION
        };
        return projection;
    }

    private int getTimeBuffer() {
        return getResources().getInteger(R.integer.time_buffer);
    }

    private String getSearchSelection(String startPoint, String endPoint, long departureTime) {
        String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
        String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
        String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + getTimeBuffer());
        String selection = selectionFrom + " and " + selectionTo + " and " + selectionDeparture;
        return selection;
    }

    private String getSortOrder() {
        return FeedEntry.COLUMN_NAME_DEPARTURE + " ASC";
    }

    private ArrayList<Ride> convertCursorToRidesList(Cursor cursor) {
        ArrayList<Ride> possibleRides = new ArrayList<>();
        while (cursor.moveToNext()) {
            Ride ride = new Ride();
            ride.Id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID));
            ride.DepartureCity = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM));
            ride.ArrivalCity = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TO));
            ride.DepartureTime = cursor.getLong(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_DEPARTURE));
            ride.ArrivalTime = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL));
            ride.Description = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION));
            possibleRides.add(ride);
        }
        return possibleRides;
    }

    private ArrayList<Ride> loadData(String startPoint, String endPoint, long departureTime, SearchType searchType) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();
        String[] projection = getSearchProjection();
        String sortOrder = getSortOrder();
        String selection;
        switch (searchType) {
            case BothCities:
                selection = getSearchSelection(startPoint, endPoint, departureTime);
                break;
            case JustArrivalCity:
                selection = getSearchSelectionArrival(endPoint, departureTime);
                break;
            case JustDepartueCity:
                selection = getSearchSelectionDeparture(startPoint, departureTime);
                break;
            default:
                selection = getSearchSelection(startPoint, endPoint, departureTime);
                break;
        }

        // Execute Query;
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, selection, null, null, null, sortOrder);

        // Convert the cursor to a list.
        return convertCursorToRidesList(cursor);
    }

    private String getSearchSelectionArrival(String endPoint, long departureTime) {
        String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
        String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + getTimeBuffer());
        String selection = selectionTo + " and " + selectionDeparture;
        return selection;
    }

    private String getSearchSelectionDeparture(String startPoint, long departureTime) {
        String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
        String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + getTimeBuffer());
        String selection = selectionFrom + " and " + selectionDeparture;
        return selection;
    }

    private List<String> createInformations(Ride ride) {

        List<String> informations = new ArrayList<>();

        Date departureDate = new Date(ride.DepartureTime * 1000);
        Date arrivalDate = new Date(ride.ArrivalTime * 1000);
        informations.add(ride.DepartureCity);
        informations.add(ride.ArrivalCity);
        DateFormat df = new SimpleDateFormat("H:mm");
        informations.add(df.format(departureDate));
        informations.add(df.format(arrivalDate));
        informations.add(ride.Description);
        return informations;
    }

    public void searchEntities() {
        AutoCompleteTextView searchTextVon = (AutoCompleteTextView) findViewById(R.id.searchTextVon);
        AutoCompleteTextView searchTextTo = (AutoCompleteTextView) findViewById(R.id.searchTextNach);

        String fromCity = searchTextVon.getText().toString();
        String toCity = searchTextTo.getText().toString();
        long timestamp = GetTimestampByCalendar(myCalendar);
        ArrayList<Ride> possibleResultList;
        SearchType searchType = getSearchType();
        switch (searchType) {
            case JustDepartueCity:
                makeToast("Keine Ankunftsstadt ausgegeben \n" + "Es werden alle Fahrten von " + fromCity + "angegeben");
                break;
            case JustArrivalCity:
                makeToast("Keine Ankunftsstadt ausgegeben \n" + "Es werden alle Fahrten zu " + toCity + "angegeben");
                break;
            default:
                break;
        }
        possibleResultList = loadData(fromCity, toCity, timestamp, searchType);
        updateExpByRides(possibleResultList);
    }

    private void updateExpByRides(ArrayList<Ride> possibleResultList) {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (Ride ride : possibleResultList) {
            List<String> informations = createInformations(ride);
            String header = Integer.toString(ride.Id);
            listDataHeader.add(header);
            listDataChild.put(header, informations);
        }
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private SearchType getSearchType() {
        AutoCompleteTextView searchTextVon = (AutoCompleteTextView) findViewById(R.id.searchTextVon);
        AutoCompleteTextView searchTextTo = (AutoCompleteTextView) findViewById(R.id.searchTextNach);
        int fromCity = searchTextVon.getText().length();
        int toCity = searchTextTo.getText().length();
        if (fromCity == 0 && toCity == 0) {
            return SearchType.NoCity;
        } else if (fromCity != 0 && toCity == 0) {
            return SearchType.JustDepartueCity;
        } else if (fromCity == 0 && toCity != 0) {
            return SearchType.JustArrivalCity;
        } else {
            return SearchType.BothCities;
        }
    }

    private void makeToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void clearExpandableListView() {
        if (listDataHeader == null || listDataChild == null)
            return;
        listDataHeader.clear();
        listDataChild.clear();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private long GetTimestampByCalendar(Calendar calendar) {
        return calendar.getTimeInMillis() / 1000;
    }
}
