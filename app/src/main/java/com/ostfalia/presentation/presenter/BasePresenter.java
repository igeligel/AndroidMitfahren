package com.ostfalia.presentation.presenter;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

/**
 * Created by Kevin on 05/05/2016.
 */
public class BasePresenter {
  private Activity activity;
  public BasePresenter(Activity activity) {
    this.activity = activity;
  }

  protected Button getButtonById(int elementId) {
    return (Button) activity.findViewById(elementId);
  }

  protected AutoCompleteTextView getAutoCompleteTextViewById(int elementId) {
    return (AutoCompleteTextView) activity.findViewById(elementId);
  }

  protected EditText getEditTextById(int elementId) {
    return (EditText) activity.findViewById(elementId);
  }

  protected ExpandableListView getExpandableListViewById(int elementId) {
    return (ExpandableListView) activity.findViewById(elementId);
  }

  protected RelativeLayout getRelativeLayoutById(int elementId) {
    return (RelativeLayout) activity.findViewById(elementId);
  }
}
