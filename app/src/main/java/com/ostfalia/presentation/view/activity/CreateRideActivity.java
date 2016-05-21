package com.ostfalia.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ostfalia.mitfahren.R;
import com.ostfalia.presentation.listeners.CreateRideListeners;
import com.ostfalia.presentation.presenter.CreateRidePresenter;
import com.ostfalia.presentation.view.model.CreateRideViewModel;

import java.util.Calendar;

/**
 * Main activity class for creating rides.
 */
public class CreateRideActivity extends BaseActivity {

  public CreateRideViewModel createRideViewModel;

  /**
   * On create Method of our CreateRideActivity.
   * Initiates every UI element and all listeners
   * @param savedInstanceState Autoparameter
   */
  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create);

    CreateRidePresenter createRidePresenter = new CreateRidePresenter(this);
    createRideViewModel = createRidePresenter.createRideViewModel;
    createRideViewModel.arrivalCalender = Calendar.getInstance();
    createRideViewModel.departureCalendar = Calendar.getInstance();
    CreateRideListeners createRideListeners = new CreateRideListeners(this);
  }

  /**
   * Method to create the Menu to change the activity
   * @param menu popup menu
   * @return true if everything works
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.game_menu, menu);
    return true;
  }

  /**
   * Method to switch the activitys, when an item of the menu is created
   * @param item popup menu
   * @return true if everything is right
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.createNew:
        return true;
      case R.id.searchNew:
        Intent newSearchIntent = new Intent(this, SearchRideActivity.class);
        newSearchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(newSearchIntent);
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
