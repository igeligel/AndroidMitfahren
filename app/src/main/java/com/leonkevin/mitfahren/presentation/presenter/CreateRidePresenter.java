package com.leonkevin.mitfahren.presentation.presenter;

import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.leon.mitfahren.R;
import com.leonkevin.mitfahren.presentation.view.activity.CreateRideActivity;
import com.leonkevin.mitfahren.presentation.view.model.CreateRideViewModel;

import java.util.ArrayList;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRidePresenter extends BasePresenter{
  CreateRideActivity createRideActivity;
  CreateRideViewModel createRideViewModel;

  public CreateRidePresenter(CreateRideActivity createRideActivity) {
    super(createRideActivity);
    this.createRideActivity = createRideActivity;
    createRideViewModel = new CreateRideViewModel();
    createRideViewModel.buttonCreateRide = getButtonCreateRide();
  }

  // TODO: AUSLAGERN IN COMPONENT FOLDER!
  private Button getButtonCreateRide()  {
    return getButtonById(R.id.buttonCreateRide);
  }


  private void setAutoComplete() {
    ArrayList<AutoCompleteTextView> autoCompleteTextViews = new ArrayList<>();
    autoCompleteTextViews.add(createRideViewModel.autoCompleteTextViewDepartureCity);
    autoCompleteTextViews.add(createRideViewModel.autoCompleteTextViewArrivalCity);
    AutoCompletePresenter autoCompletePresenter = new AutoCompletePresenter();
    autoCompletePresenter.setCitiesToAutoCompleteList(autoCompleteTextViews);
  }
}
