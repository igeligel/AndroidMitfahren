package com.leon.presentation.view.activity;

import android.os.Bundle;

import com.leon.mitfahren.R;
import com.leon.presentation.listeners.CreateRideListeners;
import com.leon.presentation.presenter.CreateRidePresenter;
import com.leon.presentation.view.model.CreateRideViewModel;

import java.util.Calendar;

/**
 * Created by Kevin on 05/05/2016.
 */
public class CreateRideActivity extends BaseActivity {
  public CreateRideViewModel createRideViewModel;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create);

    CreateRidePresenter createRidePresenter = new CreateRidePresenter(this);
    createRideViewModel = createRidePresenter.getCreateRideViewModel();
    createRideViewModel.arrivalCalender = Calendar.getInstance();
    createRideViewModel.departureCalendar = Calendar.getInstance();
    CreateRideListeners createRideListeners = new CreateRideListeners(this);

  }
}
