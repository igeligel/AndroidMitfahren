package com.ostfalia.data.database;

import android.content.Context;

/**
 * Class which is the Singleton implementation of the Database.
 */
public class Database {
  /**
   * Static feed reader database helper. This is required to instantiate the tables and such.
   */
  public static FeedReaderDbHelper feedReaderDbHelper;
  private static Database ourInstance = new Database();

  /**
   * Method to get the instance of the database.
   * @return A Database type. This is the singleton pattern.
   */
  public static Database getInstance() {
    return ourInstance;
  }

  /**
   * Private constructor for the database.
   */
  private Database() {
  }

  /**
   * Method to set the application context. This needs to be done once.
   * @param context Context of the application.
   */
  public void setApplicationContext(Context context) {
    feedReaderDbHelper = new FeedReaderDbHelper(context);
  }
}
