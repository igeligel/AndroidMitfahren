package com.leonkevin.mitfahren.presentation.view.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Kevin on 05/05/2016.
 */
public class BaseActivity extends Activity {
  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public void setContentView(int layoutId) {
    setContentView(layoutId);
  }
}
