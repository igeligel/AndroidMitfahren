package com.ostfalia.presentation.listeners;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.ostfalia.domain.models.CreateRideModel;
import com.ostfalia.domain.interactor.RideInteractor;
import com.ostfalia.domain.models.MissingCreateType;
import com.ostfalia.presentation.enums.CreateRideCalendarType;
import com.ostfalia.presentation.presenter.ToastPresenter;
import com.ostfalia.presentation.view.activity.CreateRideActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Listener Class for the Create Ride Activity
 * It implements the basic Interface ICreateListener for higher level code
 */
public class CreateRideListeners implements ICreateListeners {
  private CreateRideActivity createRideActivity;

  /**
   * Starts the Listeners
   * @param createRideActivity activity on which the listeners are set
   */
  public CreateRideListeners(CreateRideActivity createRideActivity) {
    this.createRideActivity = createRideActivity;
    setListeners();
  }

  /**
   * //TODO KEIN Plan was das genau macht
   * @param calendar
   * @param departureTimePickerSetListenerParam
   * @return
   */
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
    final CreateRideCalendarType createRideCalendarType) {
    TimePickerDialog.OnTimeSetListener localListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String myFormat = "dd-MM-yy H:mm"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        switch (createRideCalendarType) {
          case ARRIVAL:
            createRideActivity.createRideViewModel.buttonArrival.setText(sdf.format(calendar.getTime()));
            break;
          case DEPARTURE:
            createRideActivity.createRideViewModel.buttonDeparture.setText(sdf.format(calendar.getTime()));
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

  private void sendMissingSearchType(MissingCreateType type) {
    Context activityContext = createRideActivity.getApplicationContext();
    switch (type) {
      case DEPARTURE:
        ToastPresenter.makeToast("Abfahrtsort fehlt", activityContext);
        break;
      case ARRIVAL:
        ToastPresenter.makeToast("Ankunftsort fehlt", activityContext);
        break;
      case DEPARTURE_DATE:
        ToastPresenter.makeToast("Abfahrtszeit ist falsch", activityContext);
        break;
      case ARRIVAL_DATE:
        ToastPresenter.makeToast("Ankunftszeit ist falsch", activityContext);
        break;
      case DESCRIPTION:
        ToastPresenter.makeToast("Beschreibung fehlt", activityContext);
        break;
      case NONE:
        ToastPresenter.makeToast("Fahrt wurde erstellt" , activityContext);
        break;
      default:
        break;
    }
  }

  /**
   * Method to set all listeners.
   */
  @Override
  public void setListeners() {
    //Departure Listener
    final Calendar departureCalendar = createRideActivity.createRideViewModel.departureCalendar;
    final TimePickerDialog.OnTimeSetListener departureTimePickerSetListener = getTimePickerDialogOnTimeSetListener(departureCalendar, CreateRideCalendarType.DEPARTURE);
    DatePickerDialog.OnDateSetListener departureDatePickerDialogDateSetListener = getDatePickerDialogOnDateSetListener(departureCalendar, departureTimePickerSetListener);
    createRideActivity.createRideViewModel.buttonDeparture.setOnClickListener(getDatePickerDialogOnClickListener(departureDatePickerDialogDateSetListener));
    //Arrival Listener
    final Calendar arrivalCalender = createRideActivity.createRideViewModel.arrivalCalender;
    TimePickerDialog.OnTimeSetListener arrivalTimePickerSetListener = getTimePickerDialogOnTimeSetListener(arrivalCalender, CreateRideCalendarType.ARRIVAL);
    DatePickerDialog.OnDateSetListener arrivalDatePickerDateSetListener = getDatePickerDialogOnDateSetListener(arrivalCalender, arrivalTimePickerSetListener);
    createRideActivity.createRideViewModel.buttonArrival.setOnClickListener(getDatePickerDialogOnClickListener(arrivalDatePickerDateSetListener));

    createRideActivity.createRideViewModel.buttonCreateRide.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager)createRideActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(createRideActivity.getCurrentFocus().getWindowToken(), 0);

        CreateRideModel createRideModel = new CreateRideModel();
        createRideModel.DepartureCity = createRideActivity.createRideViewModel.autoCompleteTextViewDepartureCity.getText().toString();
        createRideModel.ArrivalCity = createRideActivity.createRideViewModel.autoCompleteTextViewArrivalCity.getText().toString();
        createRideModel.Description = createRideActivity.createRideViewModel.editTextDescription.getText().toString();
        createRideModel.DepartureCalendar = departureCalendar;
        createRideModel.ArrivalCalendar = arrivalCalender;
        MissingCreateType missingType = RideInteractor.createRide(createRideModel);
        sendMissingSearchType(missingType);
      }
    });
  }
}
