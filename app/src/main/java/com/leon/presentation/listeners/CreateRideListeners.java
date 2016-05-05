package com.leon.presentation.listeners;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.leon.mitfahren.R;
import com.leon.presentation.view.activity.CreateRideActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRideListeners implements ICreateListeners {
  private CreateRideActivity createRideActivity;

  public CreateRideListeners(CreateRideActivity createRideActivity) {
    this.createRideActivity = createRideActivity;
    setListeners();
  }

  private DatePickerDialog.OnDateSetListener getDatePickerDialogOnDateSetListener(
    final Calendar calendar,
    final TimePickerDialog.OnTimeSetListener departureTimePickerSetListenerParam) {
    DatePickerDialog.OnDateSetListener datePickerDialogOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
          view.getContext(),
          departureTimePickerSetListenerParam,
          calendar.get(Calendar.HOUR_OF_DAY),
          calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
      }
    };
    return datePickerDialogOnDateSetListener;
  }

  private TimePickerDialog.OnTimeSetListener getTimePickerDialogOnTimeSetListener(
    final Calendar calendar,
    final CalendarType calendarType) {
    TimePickerDialog.OnTimeSetListener localListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        switch (calendarType) {
          case Arrival:
            createRideActivity.createRideViewModel.buttonArrival.setText("Arrival");
            break;
          case Departure:
            createRideActivity.createRideViewModel.buttonDeparture.setText("Departure");
            break;
          default:
            break;
        }
      }
    };
    return localListener;
  }

  private DatePickerDialog getDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener datePickerDialogOnDateSetListener) {
    DatePickerDialog datePickerDialog = new DatePickerDialog(
      context,
      datePickerDialogOnDateSetListener,
      createRideActivity.createRideViewModel.departureCalendar.get(Calendar.YEAR),
      createRideActivity.createRideViewModel.departureCalendar.get(Calendar.MONTH),
      createRideActivity.createRideViewModel.departureCalendar.get(Calendar.DAY_OF_MONTH));
    return datePickerDialog;
  }

  private View.OnClickListener getDatePickerDialogOnClickListener(final DatePickerDialog.OnDateSetListener datePickerDialogDateSetListener) {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = getDatePickerDialog(v.getContext(), datePickerDialogDateSetListener);
        datePickerDialog.show();
      }
    };
  }

  /**
   * Method to set all listeners.
   */
  @Override
  public void setListeners() {
    final Calendar departureCalendar = createRideActivity.createRideViewModel.departureCalendar;
    TimePickerDialog.OnTimeSetListener departureTimePickerSetListener = getTimePickerDialogOnTimeSetListener(departureCalendar, CalendarType.Departure);
    DatePickerDialog.OnDateSetListener departureDatePickerDialogDateSetListener = getDatePickerDialogOnDateSetListener(departureCalendar, departureTimePickerSetListener);
    createRideActivity.createRideViewModel.buttonDeparture.setOnClickListener(getDatePickerDialogOnClickListener(departureDatePickerDialogDateSetListener));

    final Calendar arrivalCalender = createRideActivity.createRideViewModel.arrivalCalender;
    TimePickerDialog.OnTimeSetListener arrivalTimePickerSetListener = getTimePickerDialogOnTimeSetListener(arrivalCalender, CalendarType.Arrival);
    DatePickerDialog.OnDateSetListener arrivalDatePickerDateSetListener = getDatePickerDialogOnDateSetListener(arrivalCalender, arrivalTimePickerSetListener);
    createRideActivity.createRideViewModel.buttonArrival.setOnClickListener(getDatePickerDialogOnClickListener(arrivalDatePickerDateSetListener));

    createRideActivity.createRideViewModel.buttonCreateRide.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String myFormat = "dd-MM-yy H:mm"; // In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Log.d(v.getResources().getString(R.string.debug_application_name), sdf.format(departureCalendar.getTime()));
        Log.d(v.getResources().getString(R.string.debug_application_name), sdf.format(arrivalCalender.getTime()));
        Log.d(v.getResources().getString(R.string.debug_application_name), "Create clicked");
      }
    });
  }
}
