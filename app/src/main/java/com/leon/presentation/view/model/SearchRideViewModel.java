package com.leon.presentation.view.model;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import java.util.Calendar;

/**
 * Created by Kevin on 05/06/2016.
 */
public class SearchRideViewModel {
  public RelativeLayout searchField;
  public AutoCompleteTextView autoCompleteTextViewDepartureCity;
  public AutoCompleteTextView autoCompleteTextViewArrivalCity;

  public Button buttonDate;
  public Button buttonTime;
  public Button buttonSearchRide;
  public ExpandableListView expandableListViewForRides;

  public Calendar searchCalender;

  public boolean ResultViewMode;
}
