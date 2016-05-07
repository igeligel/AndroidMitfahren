package com.leon.data.repository;

import android.database.Cursor;

import com.leon.data.database.FeedReaderContract;
import com.leon.data.entity.Ride;
// TODO: FIX DEPENDENCY!
import com.leon.domain.models.SearchType;

import java.util.ArrayList;

/**
 * Created by Kevin on 05/07/2016.
 */
final class RidesRepositoryHelper {
  protected static String[] getSearchProjection() {
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

  protected static String getDefaultSearchSelection(String startPoint, String endPoint, long departureTime, long timeBuffer) {
    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + departureTime + timeBuffer;
    String selection = selectionFrom + " and " + selectionTo + " and " + selectionDeparture;
    return selection;
  }

  protected static String getSearchSelectionArrival(String endPoint, long departureTime, long timeBuffer) {
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + timeBuffer);
    String selection = selectionTo + " and " + selectionDeparture;
    return selection;
  }

  protected static String getSearchSelectionDeparture(String startPoint, long departureTime, long timeBuffer) {
    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + timeBuffer);
    String selection = selectionFrom + " and " + selectionDeparture;
    return selection;
  }

  protected static String getSearchSortOrder() {
    return FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " ASC";
  }

  protected static ArrayList<Ride> convertCursorToRidesList(Cursor cursor) {
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

  protected static String getSelection(String startPoint, String endPoint, long departureTime, SearchType searchType) {
    String selection;
    switch (searchType) {
      case BothCities:
        selection = RidesRepositoryHelper.getDefaultSearchSelection(startPoint,endPoint, departureTime, getTimeBuffer());
        break;
      case JustArrivalCity:
        selection = RidesRepositoryHelper.getSearchSelectionArrival(endPoint, departureTime, getTimeBuffer());
        break;
      case JustDepartueCity:
        selection = RidesRepositoryHelper.getSearchSelectionDeparture(startPoint, departureTime, getTimeBuffer());
        break;
      default:
        selection = RidesRepositoryHelper.getDefaultSearchSelection(startPoint, endPoint, departureTime, getTimeBuffer());
        break;
    }
    return selection;
  }

  /**
   * Returns the standard time buffer.
   * @return The value of the time buffer as unix timespan.
   */
  private static long getTimeBuffer() {
    return 21600;
  }
}
