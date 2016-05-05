package com.leonkevin.mitfahren.presentation.view.model;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRideViewModel {
  public Activity createRideActivity;
  public Button departureButton;
  public TextView textViewDepartureCity;
  public AutoCompleteTextView autoCompleteTextViewDepartureCity;
  public AutoCompleteTextView autoCompleteTextViewArrivalCity;
  public Button buttonDeparture;
  public Button buttonArrival;
  public EditText editTextDescription;
  public Button buttonCreateRide;
}
