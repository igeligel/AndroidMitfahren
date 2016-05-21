package com.ostfalia.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ostfalia.data.database.Database;
import com.ostfalia.mitfahren.R;
import com.ostfalia.presentation.listeners.SearchRideListeners;
import com.ostfalia.presentation.presenter.SearchRidePresenter;
import com.ostfalia.presentation.view.model.SearchRideViewModel;

import java.util.Calendar;

/**
 * Main activity class for searching rides.
 */
public class SearchRideActivity extends BaseActivity {
  public SearchRideViewModel searchRideViewModel;

  /**
   * On create Method of our SearchRideActivity.
   * Initiates every UI element and all listeners
   * @param savedInstanceState Autoparameter
   */
  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Database.getInstance().setApplicationContext(this);
    setContentView(R.layout.search);

    SearchRidePresenter createRidePresenter = new SearchRidePresenter(this);
    searchRideViewModel = createRidePresenter.searchRideViewModel;
    searchRideViewModel.searchCalender = Calendar.getInstance();

    SearchRideListeners searchRideListeners = new SearchRideListeners(this);
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
        Intent newCreateIntent = new Intent(this, CreateRideActivity.class);
        newCreateIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(newCreateIntent);
        finish();
        return true;
      case R.id.searchNew:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
