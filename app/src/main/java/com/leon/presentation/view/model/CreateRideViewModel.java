package com.leon.presentation.view.model;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRideViewModel {
  public AutoCompleteTextView autoCompleteTextViewDepartureCity;
  public AutoCompleteTextView autoCompleteTextViewArrivalCity;
  public Button buttonDeparture;
  public Button buttonArrival;
  public EditText editTextDescription;
  public Button buttonCreateRide;

  public Calendar departureCalendar;
  public Calendar arrivalCalender;
}
