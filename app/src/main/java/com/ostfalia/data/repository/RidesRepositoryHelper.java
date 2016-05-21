package com.ostfalia.data.repository;

import android.database.Cursor;

import com.ostfalia.data.database.FeedReaderContract;
import com.ostfalia.data.entity.Ride;
// TODO: FIX DEPENDENCY!
import com.ostfalia.domain.models.SearchType;

import java.util.ArrayList;

/**
 * Helper Class for orders to the database
 */
final class RidesRepositoryHelper {

  /**
   * Creates a Projection for searching in the database
   * @return array with all porjection values
   */
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

  /**
   * Method to create a sql query for the database
   * in this Method every search options are typed in from the user
   * @param startPoint Departure City of the Ride
   * @param endPoint  Arrival City of the Ride
   * @param departureTime Departure time of the Ride
   * @param timeBuffer time after the departure time,
   * @return Selection string for the database
   */
  protected static String getDefaultSearchSelection(String startPoint, String endPoint, long departureTime, long timeBuffer) {
    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + departureTime + timeBuffer;
    String selection = selectionFrom + " and " + selectionTo + " and " + selectionDeparture;
    return selection;
  }

  /**
   * * Method to create a sql query for the database
   * in this Method every search options expect the startpoint
   * are typed in from the user
   * @param endPoint  Arrival City of the Ride
   * @param departureTime Departure time of the Ride
   * @param timeBuffer time after the departure time,
   * @return Selection string for the database
   */
  protected static String getSearchSelectionArrival(String endPoint, long departureTime, long timeBuffer) {
    String selectionTo = FeedReaderContract.FeedEntry.COLUMN_NAME_TO + " like " + "'%" + endPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + timeBuffer);
    String selection = selectionTo + " and " + selectionDeparture;
    return selection;
  }

  /**
   * * Method to create a sql query for the database
   * in this Method every search options except the endpoint
   * are typed in from the user
   * @param startPoint Departure City of the Ride
   * @param departureTime Departure time of the Ride
   * @param timeBuffer time after the departure time,
   * @return Selection string for the database
   */
  protected static String getSearchSelectionDeparture(String startPoint, long departureTime, long timeBuffer) {
    String selectionFrom = FeedReaderContract.FeedEntry.COLUMN_NAME_FROM + " like " + "'%" + startPoint + "%'";
    String selectionDeparture = FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " between " + departureTime + " and " + (departureTime + timeBuffer);
    String selection = selectionFrom + " and " + selectionDeparture;
    return selection;
  }

  /**
   * Provides a Searchorder for the Database
   * @return searchorder, where the entries are ordered ascending on the Departure Collumn
   */
  protected static String getSearchSortOrder() {
    return FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE + " ASC";
  }

  /**
   * Method which iterates over all possible solutions and saves them in a database
   * @param cursor Cursor which points on the possible Solutions in the database
   * @return result list of possible rides
   */
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

  /**
   * Method which returns the Selection string
   * the selection differentiates on the given searchType
   * @param startPoint Departure City of the Ride
   * @param endPoint  Arrival City of the Ride
   * @param departureTime Departure time of the Ride
   * @param searchType enum, which saves, what information the user typed in
   * @return selection String for the database
   */
  protected static String getSelection(String startPoint, String endPoint, long departureTime, SearchType searchType) {
    String selection;
    switch (searchType) {
      case BOTH_CITIES:
        selection = RidesRepositoryHelper.getDefaultSearchSelection(startPoint,endPoint, departureTime, getTimeBuffer());
        break;
      case JUST_ARRIVAL_CITY:
        selection = RidesRepositoryHelper.getSearchSelectionArrival(endPoint, departureTime, getTimeBuffer());
        break;
      case JUST_DEPARTURE_CITY:
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
   * This Timebuffer is 6 hours long.
   * @return The value of the time buffer as unix timespan.
   */
  private static long getTimeBuffer() {
    return 21600;
  }
}
