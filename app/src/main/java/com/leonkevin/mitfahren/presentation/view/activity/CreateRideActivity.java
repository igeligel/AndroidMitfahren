package com.leonkevin.mitfahren.presentation.view.activity;

import com.leon.mitfahren.R;
import com.leonkevin.mitfahren.presentation.presenter.CreateRidePresenter;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRideActivity extends BaseActivity {

  public CreateRideActivity() {
    setContentView(R.layout.create);
    CreateRidePresenter createRidePresenter = new CreateRidePresenter(this);
  }
}
