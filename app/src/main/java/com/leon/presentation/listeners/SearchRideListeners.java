package com.leon.presentation.listeners;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.leon.data.entity.Ride;
import com.leon.domain.SearchRideModel;
import com.leon.domain.interactor.RideInteractor;
import com.leon.presentation.enums.SearchRideCalendarType;
import com.leon.presentation.view.activity.SearchRideActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kevin on 05/06/2016.
 */
public class SearchRideListeners implements ICreateListeners {

  private SearchRideActivity searchRideActivity;

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

  @Override
  public void setListeners() {
    final DatePickerDialog.OnDateSetListener datePickerDialogOnDateSetListener = getDatePickerDialogOnDateSetListener();
    searchRideActivity.searchRideViewModel.buttonDate.setOnClickListener(getButtonDateOnClickListener(datePickerDialogOnDateSetListener));

    final TimePickerDialog.OnTimeSetListener timePickerDialogOnTimeSetListener = getTimePickerDialogOnTimeSetListener();
    searchRideActivity.searchRideViewModel.buttonTime.setOnClickListener(getButtonTimeOnClickListener(timePickerDialogOnTimeSetListener));

    searchRideActivity.searchRideViewModel.buttonSearchRide.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        long timestamp = searchRideActivity.searchRideViewModel.searchCalender.getTimeInMillis();
        SearchRideModel searchRideModel = new SearchRideModel();
        searchRideModel.DepartureCity = searchRideActivity.searchRideViewModel.autoCompleteTextViewDepartureCity.getText().toString();
        searchRideModel.ArrivalCity = searchRideActivity.searchRideViewModel.autoCompleteTextViewArrivalCity.getText().toString();
        searchRideModel.Calendar = searchRideActivity.searchRideViewModel.searchCalender;
        List<Ride> rides = RideInteractor.getRides(searchRideModel);
      }
    });
  }
}
