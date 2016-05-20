package com.ostfalia.presentation.presenter;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.ostfalia.mitfahren.R;
import com.ostfalia.presentation.view.activity.SearchRideActivity;
import com.ostfalia.presentation.view.adapter.CitiesAdapter;
import com.ostfalia.presentation.view.model.SearchRideViewModel;

import java.util.ArrayList;

/**
 * Created by Kevin on 05/05/2016.
 */
public class SearchRidePresenter extends BasePresenter{
  public SearchRideViewModel searchRideViewModel;
  private final SearchRideActivity searchRideActivity;

  public SearchRidePresenter(SearchRideActivity searchRideActivity) {
    super(searchRideActivity);
    this.searchRideActivity = searchRideActivity;
    searchRideViewModel = new SearchRideViewModel();
    searchRideViewModel.ResultViewMode = false;
    searchRideViewModel.autoCompleteTextViewDepartureCity = getAutoCompleteTextViewDepartureCity();
    searchRideViewModel.autoCompleteTextViewArrivalCity = getAutoCompleteTextViewArrivalCity();
    searchRideViewModel.buttonDate = getButtonDate();
    searchRideViewModel.buttonTime = getButtonTime();
    searchRideViewModel.buttonSearchRide = getButtonSearchRide();
    searchRideViewModel.expandableListViewForRides = getExpandableListViewForRides();
    searchRideViewModel.searchField = getRelativeLayoutSearchField();
    searchRideViewModel.ResultViewMode = false;
    setAutoComplete();
    setAdapters();
  }

  private AutoCompleteTextView getAutoCompleteTextViewDepartureCity() {
    return getAutoCompleteTextViewById(R.id.searchAutoCompleteTextViewDepartureCity);
  }

  private AutoCompleteTextView getAutoCompleteTextViewArrivalCity() {
    return getAutoCompleteTextViewById(R.id.searchAutoCompleteTextViewArrivalCity);
  }

  private Button getButtonDate() {
    return getButtonById(R.id.searchButtonDate);
  }

  private Button getButtonTime() {
    return getButtonById(R.id.searchButtonTime);
  }

  private Button getButtonSearchRide() {
    return getButtonById(R.id.searchButtonSearch);
  }

  private ExpandableListView getExpandableListViewForRides() {
    return getExpandableListViewById(R.id.searchExpandableListViewRideList);
  }

  private RelativeLayout getRelativeLayoutSearchField() {
    return getRelativeLayoutById(R.id.searchRelativeLayoutSearchField);
  }

  private void setAdapters() {
    searchRideViewModel.autoCompleteTextViewDepartureCity.setAdapter(getStandardCitiesAdapter());
    searchRideViewModel.autoCompleteTextViewArrivalCity.setAdapter(getStandardCitiesAdapter());
  }

  private CitiesAdapter getStandardCitiesAdapter() {
    return new CitiesAdapter(searchRideActivity,android.R.layout.simple_list_item_1, getCities());
  }

  private String[] getCities() {
    return searchRideActivity.getResources().getStringArray(R.array.cities_array);
  }

  private void setAutoComplete() {
    ArrayList<AutoCompleteTextView> autoCompleteTextViews = new ArrayList<>();
    autoCompleteTextViews.add(searchRideViewModel.autoCompleteTextViewDepartureCity);
    autoCompleteTextViews.add(searchRideViewModel.autoCompleteTextViewArrivalCity);
    AutoCompletePresenter autoCompletePresenter = new AutoCompletePresenter();
    autoCompletePresenter.setCitiesToAutoCompleteList(autoCompleteTextViews);
  }
}
