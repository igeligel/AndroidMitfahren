package com.leon.mitfahren;

// import com.leon.mitfahren.FeedReaderContract.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leon on 20.04.16.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
  // If you change the database schema, you must increment the database version.

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "drive.db";


  private static final String TEXT_TYPE = " TEXT";
  private static final String INTEGER_TYPE = " INTEGER";
  private static final String COMMA_SEP = ",";
  private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " ("
              + FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID
              + " INTEGER PRIMARY KEY AUTOINCREMENT,"
              + FeedReaderContract.FeedEntry.COLUMN_NAME_YEAR + INTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_MONTH + INTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_DAY + INTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_HOUR + INTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_MINUTE + INTEGER_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + TEXT_TYPE + COMMA_SEP
              + FeedReaderContract.FeedEntry.COLUMN_NAME_TO + TEXT_TYPE
              + " );";

  private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

  public FeedReaderDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_ENTRIES);
  }

  /**
   * Method to upgrade the database.
   * @param db Database to update.
   * @param oldVersion OldVersion.
   * @param newVersion NewVersion.
   */
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL(SQL_DELETE_ENTRIES);
    onCreate(db);
  }

  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }
}