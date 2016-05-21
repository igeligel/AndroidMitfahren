package com.ostfalia.presentation.view.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Adapter for the listed cities
 */
public class CitiesAdapter extends ArrayAdapter<String>{
  public CitiesAdapter(Context context, int resource, String[] objects) {
    super(context, resource, objects);
  }
}
