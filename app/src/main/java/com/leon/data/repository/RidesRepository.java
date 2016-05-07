package com.leon.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.leon.data.database.Database;
import com.leon.data.database.FeedReaderContract;
import com.leon.data.database.FeedReaderDbHelper;
import com.leon.data.entity.Ride;
import com.leon.models.SearchType;

import java.util.ArrayList;

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

  public static ArrayList<Ride> SearchRides(String startPoint, String endPoint, long departureTime, SearchType searchType) {
    FeedReaderDbHelper feedReaderDbHelper = Database.feedReaderDbHelper;
    SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();
    String[] projection = getSearchProjection();
    String sortOrder = getSortOrder();
    String selection;
    switch (searchType) {
      case BothCities:
        selection = getSearchSelection(startPoint,endPoint, departureTime);
        break;
      case JustArrivalCity:
        selection = getSearchSelectionArrival(endPoint, departureTime);
        break;
      case JustDepartueCity:
        selection = getSearchSelectionDeparture(startPoint, departureTime);
        break;
      default:
        selection = getSearchSelection(startPoint, endPoint, departureTime);
        break;
    }
    Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, selection, null, null, null, sortOrder);

    // Convert the cursor to a list.
    return convertCursorToRidesList(cursor);
  }

  private static ArrayList<Ride> convertCursorToRidesList(Cursor cursor) {
    ArrayList<Ride> possibleRides = new ArrayList<>();
    while (cursor.moveToNext()) {
      Ride ride = new Ride();
      ride.Id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID));
      ride.DepartureCity = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM));
      ride.ArrivalCity = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TO));
      ride.DepartureTimestamp = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE));
      ride.ArrivalTimestamp = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL));
      ride.Description = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION));
      possibleRides.add(ride);
    }
    return possibleRides;
  }

  private static String getSortOrder() {
    return FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " ASC";
  }

  private static int getTimeBuffer() {
    return 21600;
  }

  private static String getSearchSelection(String startPoint, String endPoint, long departureTime) {
    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + departureTime + getTimeBuffer();
    String selection = selectionFrom + " and " + selectionTo + " and " + selectionDeparture;
    return selection;
  }

  private static String getSearchSelectionArrival(String endPoint, long departureTime) {
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + getTimeBuffer());
    String selection = selectionTo + " and " + selectionDeparture;
    return selection;
  }

  private static String getSearchSelectionDeparture(String startPoint, long departureTime) {
    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + getTimeBuffer());
    String selection = selectionFrom + " and " + selectionDeparture;
    return selection;
  }

  private static String[] getSearchProjection() {
    String[] projection = {
      FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
      FeedReaderContract.FeedEntry.COLUMN_NAME_FROM,
      FeedReaderContract.FeedEntry.COLUMN_NAME_TO,
      FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL,
      FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE,
      FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION
    };
    return projection;
  }
}
