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
 * Created by Kevin on 05/05/2016.
 */
public class SearchRideActivity extends BaseActivity {
  public SearchRideViewModel searchRideViewModel;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Database.getInstance().SetApplicationContext(this);
    setContentView(R.layout.search);

    SearchRidePresenter createRidePresenter = new SearchRidePresenter(this);
    searchRideViewModel = createRidePresenter.searchRideViewModel;
    searchRideViewModel.searchCalender = Calendar.getInstance();

    SearchRideListeners searchRideListeners = new SearchRideListeners(this);
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
