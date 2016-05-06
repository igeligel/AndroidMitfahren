package com.leon.data.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.leon.data.database.Database;
import com.leon.data.database.FeedReaderContract;
import com.leon.data.database.FeedReaderDbHelper;
import com.leon.data.entity.Ride;
import com.leon.mitfahren.ToastManager;

/**
 * Created by Kevin on 05/06/2016.
 */
public final class RidesRepository {
  public static void InsertOrUpdateRide(Ride ride) {
    ContentValues content = new ContentValues();
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM, ride.DepartureCity);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TO, ride.ArrivalCity);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE, ride.DepartureTimestamp);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL, ride.ArrivalTimestamp);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, ride.Description);
    long newRowId;
    SQLiteDatabase db = Database.getInstance().feedReaderDbHelper.getWritableDatabase();
    newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, content);
    Log.d("New Row Id:", Long.toString(newRowId));
  }

  public static void SearchRides() {

  }
}
