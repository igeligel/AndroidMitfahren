package com.ostfalia.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Feed reader database helper. Here are configurations of the database and table schemes.
 * If you change the database scheme, you need to increment the database version.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 4;
  private static final String DATABASE_NAME = "drive.db";
  private static final String TEXT_TYPE = " TEXT";
  private static final String BIGINTEGER_TYPE = " BIGINTEGER";
  private static final String COMMA_SEP = ",";
  private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " ("
              + FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID
              + " INTEGER PRIMARY KEY AUTOINCREMENT,"
              + FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + TEXT_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_TO + TEXT_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + BIGINTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL + BIGINTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE
              + " );";

  private static final String SQL_DELETE_ENTRIES =
      "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

  /**
   * Constructor for the feed reader database helper.
   * This will use the constructor of SQLiteOpenHelper.
   * @param context Application context which should be used.
   */
  public FeedReaderDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Method which is implemented by the interface.
   * @param db Database which should be created.
   */
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_ENTRIES);
  }

  /**
   * Method which will be called when the database gets upgraded.
   * @param db Database to update.
   * @param oldVersion Old version of the database.
   * @param newVersion New version of the database.
   */
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_ENTRIES);
    onCreate(db);
  }

  /**
   * Method which is called when the database gets downgraded.
   * @param db Database to downgrade.
   * @param oldVersion Old version of the database.
   * @param newVersion New version of the database.
   */
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }
}