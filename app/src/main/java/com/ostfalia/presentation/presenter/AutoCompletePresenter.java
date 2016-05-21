package com.ostfalia.presentation.presenter;

import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * Presenter class for the Autocomplete Text
 */
public class AutoCompletePresenter {

  /**
   * Sets a city for every AutocompleteText
   * @param autoCompleteTextViews
   */
  public void setCitiesToAutoCompleteList(ArrayList<AutoCompleteTextView> autoCompleteTextViews) {
    for (AutoCompleteTextView autoCompleteTextView: autoCompleteTextViews) {
      setCitiesToAutoComplete(autoCompleteTextView);
    }
  }

  //TODO set the Adapter not to null
  public void setCitiesToAutoComplete(AutoCompleteTextView autoCompleteTextView) {
    autoCompleteTextView.setAdapter(null);
  }
}
