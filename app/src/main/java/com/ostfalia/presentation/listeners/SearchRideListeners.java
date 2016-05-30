package com.ostfalia.presentation.listeners;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ostfalia.data.entity.Ride;
import com.ostfalia.domain.models.MissingSearchType;
import com.ostfalia.domain.models.SearchResult;
import com.ostfalia.domain.models.SearchRideModel;
import com.ostfalia.domain.interactor.RideInteractor;
import com.ostfalia.presentation.presenter.ToastPresenter;
import com.ostfalia.presentation.view.adapter.ExpandableListAdapter;
import com.ostfalia.mitfahren.R;
import com.ostfalia.presentation.view.activity.SearchRideActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class SearchRideListeners implements ICreateListeners {

  private SearchRideActivity searchRideActivity;
  ExpandableListAdapter listAdapter;

  List<String> listDataHeader;
  HashMap<String, List<String>> listDataChild;


  public SearchRideListeners(SearchRideActivity searchRideActivity) {
    this.searchRideActivity = searchRideActivity;
    setListeners();
  }

  private DatePickerDialog.OnDateSetListener getDatePickerDialogOnDateSetListener() {
    DatePickerDialog.OnDateSetListener datePickerDialogOnSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar currentCalendar = searchRideActivity.searchRideViewModel.searchCalender;
        currentCalendar.set(Calendar.YEAR, year);
        currentCalendar.set(Calendar.MONTH, monthOfYear);
        currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd-MM-yy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        searchRideActivity.searchRideViewModel.buttonDate.setText(sdf.format(currentCalendar.getTime()));

      }
    };
    return datePickerDialogOnSetListener;
  }

  private TimePickerDialog.OnTimeSetListener getTimePickerDialogOnTimeSetListener() {
    TimePickerDialog.OnTimeSetListener timePickerDialogOnSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar currentCalendar = searchRideActivity.searchRideViewModel.searchCalender;
        currentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        currentCalendar.set(Calendar.MINUTE, minute);
        String myFormat = "H:mm"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        searchRideActivity.searchRideViewModel.buttonTime.setText(sdf.format(currentCalendar.getTime()));
      }
    };
    return timePickerDialogOnSetListener;
  }

  private View.OnClickListener getButtonDateOnClickListener(final DatePickerDialog.OnDateSetListener datePickerDialogOnDateSetListener) {
    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
          v.getContext(),
          datePickerDialogOnDateSetListener,
          searchRideActivity.searchRideViewModel.searchCalender.get(Calendar.YEAR),
          searchRideActivity.searchRideViewModel.searchCalender.get(Calendar.MONTH),
          searchRideActivity.searchRideViewModel.searchCalender.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
      }
    };
    return onClickListener;
  }

  private View.OnClickListener getButtonTimeOnClickListener(final TimePickerDialog.OnTimeSetListener timePickerDialogOnTimeSetListener) {
    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
          v.getContext(),
          timePickerDialogOnTimeSetListener,
          searchRideActivity.searchRideViewModel.searchCalender.get(Calendar.HOUR_OF_DAY),
          searchRideActivity.searchRideViewModel.searchCalender.get(Calendar.MINUTE), true);
        timePickerDialog.show();
      }
    };
    return onClickListener;
  }

  private void printResultToast(MissingSearchType missingType) {
    if (searchRideActivity.searchRideViewModel.ResultViewMode) return;
    Context activityContext = searchRideActivity.getApplicationContext();
    switch (missingType) {
      case ALL:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_AllMissing),
                activityContext);
        break;
      case DEPARTURE_AND_ARRIVAL:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_DeparuteAndArrivalMissing),
                activityContext);
        break;
      case DEPARTURE_AND_TIME:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_DepartueAndTimeMissing),
                activityContext);
        break;
      case ARRIVAL_AND_TIME:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_ArrivalAndTimeMissing),
                activityContext);
        break;
      case DEPARTURE:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_DepartureMissing),
                activityContext);
        break;
      case ARRIVAL:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_ArrivalMissing),
                activityContext);
        break;
      case TIME:
        ToastPresenter.makeToast(activityContext.getString(R.string.search_TimeMissing),
                activityContext);
        break;
      case NONE:
        break;
      default:
        break;
    }
  }

  @Override
  public void setListeners() {
    final DatePickerDialog.OnDateSetListener datePickerDialogOnDateSetListener = getDatePickerDialogOnDateSetListener();
    searchRideActivity.searchRideViewModel.buttonDate.setOnClickListener(getButtonDateOnClickListener(datePickerDialogOnDateSetListener));

    final TimePickerDialog.OnTimeSetListener timePickerDialogOnTimeSetListener = getTimePickerDialogOnTimeSetListener();
    searchRideActivity.searchRideViewModel.buttonTime.setOnClickListener(getButtonTimeOnClickListener(timePickerDialogOnTimeSetListener));

    searchRideActivity.searchRideViewModel.buttonSearchRide.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager)searchRideActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(searchRideActivity.getCurrentFocus().getWindowToken(), 0);
        SearchRideModel searchRideModel = new SearchRideModel();
        searchRideModel.DepartureCity = searchRideActivity.searchRideViewModel.autoCompleteTextViewDepartureCity.getText().toString();
        searchRideModel.ArrivalCity = searchRideActivity.searchRideViewModel.autoCompleteTextViewArrivalCity.getText().toString();
        searchRideModel.Calendar = searchRideActivity.searchRideViewModel.searchCalender;
        SearchResult ridesResult = RideInteractor.getRides(searchRideModel);
        printResultToast(ridesResult.getMissingType());
        ArrayList<Ride> rides = ridesResult.getRideList();
        if (rides.size() == 0) {
          ToastPresenter.makeToast(searchRideActivity.getString(R.string.search_NoRideFound),
                  searchRideActivity.getApplicationContext());
          return;
        }
        // TOGGLE VIEW STATE
        updateExpByRides(rides);
        toogleViewState();
      }
    });
  }

  private void toogleViewState() {
    RelativeLayout.LayoutParams buttonLayoutParams = (RelativeLayout.LayoutParams) searchRideActivity.searchRideViewModel.buttonSearchRide.getLayoutParams();
    // Check for search mode.
    if (searchRideActivity.searchRideViewModel.ResultViewMode == true) {
      searchRideActivity.searchRideViewModel.searchField.setVisibility(View.VISIBLE);
      buttonLayoutParams.addRule(RelativeLayout.BELOW, R.id.searchRelativeLayoutSearchField);
      searchRideActivity.searchRideViewModel.buttonSearchRide.setLayoutParams(buttonLayoutParams);
      clearExpandableListView();
      searchRideActivity.searchRideViewModel.ResultViewMode = false;
    } else {
      searchRideActivity.searchRideViewModel.searchField.setVisibility(View.INVISIBLE);
      buttonLayoutParams.addRule(RelativeLayout.BELOW, R.id.searchRelativeLayoutPlaceholder);
      searchRideActivity.searchRideViewModel.buttonSearchRide.setLayoutParams(buttonLayoutParams);
      searchRideActivity.searchRideViewModel.ResultViewMode = true;
    }
  }

  private void clearExpandableListView() {
    if (listDataHeader == null || listDataChild == null)
      return;
    listDataHeader.clear();
    listDataChild.clear();
    listAdapter = new ExpandableListAdapter(searchRideActivity, listDataHeader, listDataChild);
    searchRideActivity.searchRideViewModel.expandableListViewForRides.setAdapter(listAdapter);
  }

  private void updateExpByRides(ArrayList<Ride> ridesForSelection) {
    listDataHeader = new ArrayList<>();
    listDataChild = new HashMap<>();
    for (Ride ride : ridesForSelection) {
      List<String> informations = createInformations(ride);
      String header = Integer.toString(ride.Id);
      listDataHeader.add(header);
      listDataChild.put(header, informations);
    }
    listAdapter = new ExpandableListAdapter(searchRideActivity, listDataHeader, listDataChild);
    searchRideActivity.searchRideViewModel.expandableListViewForRides.setAdapter(listAdapter);
  }

  private List<String> createInformations(Ride ride) {
    List<String> informations = new ArrayList<>();
    Date departureDate = new Date(ride.DepartureTimestamp * 1000);
    Date arrivalDate = new Date(ride.ArrivalTimestamp * 1000);
    informations.add(ride.DepartureCity);
    informations.add(ride.ArrivalCity);
    DateFormat df = new SimpleDateFormat("H:mm");
    informations.add(df.format(departureDate));
    informations.add(df.format(arrivalDate));
    informations.add(ride.Description);
    return informations;
  }
}
