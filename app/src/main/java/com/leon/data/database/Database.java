package com.leon.data.database;

import android.content.Context;

import com.leon.mitfahren.MainActivity;

/**
 * Created by Kevin on 05/06/2016.
 */
public class Database {
  public static FeedReaderDbHelper feedReaderDbHelper;
  private static Database ourInstance = new Database();

  public static Database getInstance() {
    return ourInstance;
  }

  private Database() {
  }

  public void SetApplicationContext(Context context) {
    feedReaderDbHelper = new FeedReaderDbHelper(context);
  }
}
