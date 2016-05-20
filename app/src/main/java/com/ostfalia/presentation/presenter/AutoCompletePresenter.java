package com.ostfalia.presentation.presenter;

import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 05/05/2016.
 */
public class AutoCompletePresenter {

  public void setCitiesToAutoCompleteList(ArrayList<AutoCompleteTextView> autoCompleteTextViews) {
    for (AutoCompleteTextView autoCompleteTextView: autoCompleteTextViews) {
      setCitiesToAutoComplete(autoCompleteTextView);
    }
  }

  public void setCitiesToAutoComplete(AutoCompleteTextView autoCompleteTextView) {
    autoCompleteTextView.setAdapter(null);
  }
}
