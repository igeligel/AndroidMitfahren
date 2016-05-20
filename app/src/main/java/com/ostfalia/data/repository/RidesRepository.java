package com.ostfalia.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ostfalia.data.database.Database;
import com.ostfalia.data.database.FeedReaderContract;
import com.ostfalia.data.database.FeedReaderDbHelper;
import com.ostfalia.data.entity.Ride;
// TODO: WRONG DEPENDENCY!
import com.ostfalia.domain.models.SearchType;

import java.util.ArrayList;

/**
 * Rides repository which will save all rides in the repository.
 */
public final class RidesRepository {

  /**
   * Inserts or update a ride in our repository.
   * @param ride Ride which will be updated or inserted.
   */
  public static void InsertOrUpdateRide(Ride ride) {
    ContentValues content = new ContentValues();
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM, ride.DepartureCity);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TO, ride.ArrivalCity);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE, ride.DepartureTimestamp);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL, ride.ArrivalTimestamp);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, ride.Description);

    SQLiteDatabase db = Database.getInstance().feedReaderDbHelper.getWritableDatabase();
    db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, content);
    // TODO: Return an instance of the object!
    // newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, content)
    // long newRowId;
  }

  // TODO: CHANGE TO A MODEL INSTEAD OF PARAMETERS!
  public static ArrayList<Ride> SearchRides(String startPoint, String endPoint, long departureTime, SearchType searchType) {
    FeedReaderDbHelper feedReaderDbHelper = Database.feedReaderDbHelper;
    SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();
    String[] projection = RidesRepositoryHelper.getSearchProjection();
    String sortOrder = RidesRepositoryHelper.getSearchSortOrder();
    String selection = RidesRepositoryHelper.getSelection(startPoint, endPoint, departureTime, searchType);
    Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, selection, null, null, null, sortOrder);
    return RidesRepositoryHelper.convertCursorToRidesList(cursor);
  }
}
