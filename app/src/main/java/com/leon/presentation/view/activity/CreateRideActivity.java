package com.leon.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.leon.mitfahren.Create;
import com.leon.mitfahren.MainActivity;
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.game_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.search:
        Intent searchIntent = new Intent(this, MainActivity.class);
        searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(searchIntent);
        finish();
        return true;
      case R.id.create:
        Intent createIntent = new Intent(this, Create.class);
        createIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(createIntent);
        finish();
        return true;
      case R.id.createNew:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
