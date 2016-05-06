package com.leon.presentation.presenter;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.leon.mitfahren.R;
import com.leon.presentation.view.activity.CreateRideActivity;
import com.leon.presentation.view.adapter.CitiesAdapter;
import com.leon.presentation.view.model.CreateRideViewModel;

import java.util.ArrayList;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRidePresenter extends BasePresenter{
  public CreateRideViewModel createRideViewModel;
  private final CreateRideActivity  createRideActivity;

  public CreateRidePresenter(CreateRideActivity createRideActivity) {
    super(createRideActivity);
    this.createRideActivity = createRideActivity;
    createRideViewModel = new CreateRideViewModel();

    // Create UI Elements.
    createRideViewModel.autoCompleteTextViewDepartureCity = getAutoCompleteTextViewDepartureCity();
    createRideViewModel.autoCompleteTextViewArrivalCity = getAutoCompleteTextViewArrivalCity();
    createRideViewModel.buttonDeparture = getButtonDeparture();
    createRideViewModel.buttonArrival = getButtonArrival();
    createRideViewModel.editTextDescription = getEditTextDescription();
    createRideViewModel.buttonCreateRide = getButtonCreateRide();
    setAutoComplete();
    setAdapters();
  }

  private Button getButtonCreateRide()  {
    return getButtonById(R.id.buttonCreateRide);
  }

  private AutoCompleteTextView getAutoCompleteTextViewDepartureCity() {
    return getAutoCompleteTextViewById(R.id.autoCompleteTextViewDepartureCity);
  }

  private AutoCompleteTextView getAutoCompleteTextViewArrivalCity() {
    return getAutoCompleteTextViewById(R.id.autoCompleteTextViewArrivalCity);
  }

  private Button getButtonDeparture() {
    return getButtonById(R.id.buttonDeparture);
  }

  private Button getButtonArrival() {
    return getButtonById(R.id.buttonArrival);
  }

  private EditText getEditTextDescription() {
    return getEditTextById(R.id.editTextDescription);
  }

  private void setAdapters() {
    createRideViewModel.autoCompleteTextViewDepartureCity.setAdapter(getStandardCitiesAdapter());
    createRideViewModel.autoCompleteTextViewArrivalCity.setAdapter(getStandardCitiesAdapter());
  }

  private CitiesAdapter getStandardCitiesAdapter() {
    return new CitiesAdapter(createRideActivity,android.R.layout.simple_list_item_1, getCities());
  }

  private String[] getCities() {
    return createRideActivity.getResources().getStringArray(R.array.cities_array);
  }

  private void setAutoComplete() {
    ArrayList<AutoCompleteTextView> autoCompleteTextViews = new ArrayList<>();
    autoCompleteTextViews.add(createRideViewModel.autoCompleteTextViewDepartureCity);
    autoCompleteTextViews.add(createRideViewModel.autoCompleteTextViewArrivalCity);
    AutoCompletePresenter autoCompletePresenter = new AutoCompletePresenter();
    autoCompletePresenter.setCitiesToAutoCompleteList(autoCompleteTextViews);
  }

  private String getTextFromAutoCompleteText(AutoCompleteTextView autoCompleteTextView) {
    return autoCompleteTextView.getText().toString();
  }

  private String getTextFromEditText(EditText editText) {
    return editText.getText().toString();
  }

  public CreateRideViewModel getCreateRideViewModel() {
    return this.createRideViewModel;
  }
}
