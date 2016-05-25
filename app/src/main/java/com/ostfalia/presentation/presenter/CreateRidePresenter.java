package com.ostfalia.presentation.presenter;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.ostfalia.mitfahren.R;
import com.ostfalia.presentation.view.activity.CreateRideActivity;
import com.ostfalia.presentation.view.model.CreateRideViewModel;

import java.util.ArrayList;

/**
 * Presenter for the CreateRide Activity
 */
public class CreateRidePresenter extends BasePresenter{
  public CreateRideViewModel createRideViewModel;
  private final CreateRideActivity  createRideActivity;

  /**
   * Constructor
   * Initiates all UI Elements
   * @param createRideActivity activ activity
   */
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

  /**
   * Initiates all Cities in the Adapter
   */
  private void setAdapters() {
    createRideViewModel.autoCompleteTextViewDepartureCity.setAdapter(getStandardCitiesAdapter());
    createRideViewModel.autoCompleteTextViewArrivalCity.setAdapter(getStandardCitiesAdapter());

  }

  private ArrayAdapter getStandardCitiesAdapter() {
    return new ArrayAdapter<String>(createRideActivity.getApplicationContext()
            , R.layout.autocompletetext, getCities());
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

  /**
   * Method to get the Datastructure of the Creation
   * @return Model of informations you need to create a new Ride
   */
  public CreateRideViewModel getCreateRideViewModel() {
    return this.createRideViewModel;
  }
}
