package com.ostfalia.presentation.view.model;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import java.util.Calendar;

/**
 * Viewmodel for searching rides.
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
