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
    public static final String COLUMN_NAME_FROM = "driveFrom";
    public static final String COLUMN_NAME_TO = "driveTo";
    public static final String COLUMN_NAME_DEPARTURE = "departure";
    public static final String COLUMN_NAME_ARRIVAL = "arrival";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
  }
}
