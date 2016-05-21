package com.ostfalia.presentation.presenter;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

/**
 * Base Presenter for our two activities.
 * It holds predefined methods for the classes, who inherits from this.
 */
public class BasePresenter {
  private Activity activity;

  /**
   * Basic constructor
   * @param activity activity for the presenter
   */
  public BasePresenter(Activity activity) {
    this.activity = activity;
  }

  /**
   * Method to get buttons in the activity
   * @param elementId id of the button
   * @return button element
   */
  protected Button getButtonById(int elementId) {
    return (Button) activity.findViewById(elementId);
  }

  /**
   * Method to get AutoCompleteTextViews in the activity
   * @param elementId id of the AutoCompleteTextView
   * @return AutoCompleteTextView element
   */
  protected AutoCompleteTextView getAutoCompleteTextViewById(int elementId) {
    return (AutoCompleteTextView) activity.findViewById(elementId);
  }

  /**
   * Method to get EditText in the activity
   * @param elementId id of the EditText
   * @return EditText element
   */
  protected EditText getEditTextById(int elementId) {
    return (EditText) activity.findViewById(elementId);
  }

  /**
   * Method to get ExpandableListView in the activity
   * @param elementId id of the ExpandableListView
   * @return ExpandableListView element
   */
  protected ExpandableListView getExpandableListViewById(int elementId) {
    return (ExpandableListView) activity.findViewById(elementId);
  }

  /**
   * Method to get the RelativeLayout in the activity
   * @param elementId id of the RelativeLayout
   * @return RelativeLayout element
   */
  protected RelativeLayout getRelativeLayoutById(int elementId) {
    return (RelativeLayout) activity.findViewById(elementId);
  }
}
