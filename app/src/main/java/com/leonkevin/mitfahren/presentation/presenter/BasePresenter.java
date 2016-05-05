package com.leonkevin.mitfahren.presentation.presenter;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kevin on 05/05/2016.
 */
public class BasePresenter {
  private Activity activity;
  public BasePresenter(Activity activity) {
    this.activity = activity;
  }
  public Button getButtonById(int elementId) {
    return (Button) activity.findViewById(elementId);
  }

  public AutoCompleteTextView getAutoCompleteTextViewById(int elementId) {
    return (AutoCompleteTextView) activity.findViewById(elementId);
  }

  public EditText getEditTextById(int elementId) {
    return (EditText) activity.findViewById(elementId);
  }
}
