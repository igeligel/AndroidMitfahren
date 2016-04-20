package com.leon.mitfahren;

import android.provider.BaseColumns;

/**
 * Created by Leon on 20.04.16.
 */
public final class FeedReaderContract {
  // To prevent someone from accidentally instantiating the contract class,
  // give it an empty constructor.
  public FeedReaderContract() {}

  /* Inner class that defines the table contents */
  public static abstract class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "drive";
    public static final String COLUMN_NAME_ENTRY_ID = "driveid";
    public static final String COLUMN_NAME_YEAR = "year";
    public static final String COLUMN_NAME_MONTH = "month";
    public static final String COLUMN_NAME_DAY = "day";
    public static final String COLUMN_NAME_HOUR = "hour";
    public static final String COLUMN_NAME_MINUTE = "minute";
    public static final String COLUMN_NAME_FROM = "driveFrom";
    public static final String COLUMN_NAME_TO = "driveTo";
  }
}
