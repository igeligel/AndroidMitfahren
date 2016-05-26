package com.ostfalia.domain.validator;

import android.util.Log;

import com.ostfalia.domain.models.MissingSearchType;
import com.ostfalia.domain.models.SearchRideModel;
import com.ostfalia.presentation.presenter.ToastPresenter;

import java.util.Calendar;

/**
 * Validator for the search of rides
 */
public final class SearchRideValidator {
  /**
   * Validates the given model
   * @param searchRideModel given Model
   * @return missing input type
   */
  public static MissingSearchType IsModelValid(SearchRideModel searchRideModel) {
    boolean validDepartureCity = true;
    boolean validArrivalCity = true;
    boolean validTime = true;

    if(searchRideModel.DepartureCity.length() == 0) {
      validDepartureCity = false;
    }
    if(searchRideModel.ArrivalCity.length() == 0) {
      validArrivalCity = false;
    }
    long inputTime = searchRideModel.Calendar.getTimeInMillis();
    long actualTime = Calendar.getInstance().getTimeInMillis();
    if(inputTime <= actualTime && inputTime > actualTime - 100000) {
      validTime = false;
    }

    return validateMissingSearchType(validDepartureCity, validArrivalCity, validTime);
  }

  /**
   * Method to get the Missing Type of the given Searchtype
   * @param validDepartureCity is the departure city valid
   * @param validArrivalCity is the arrival city valid
   * @param validTime is the time valid
   * @return type of the valid values
   */
  private static  MissingSearchType validateMissingSearchType(
          boolean validDepartureCity, boolean validArrivalCity, boolean validTime) {

    if (!validDepartureCity && !validArrivalCity && !validTime) {
      return MissingSearchType.ALL;
    } else if (!validDepartureCity && !validArrivalCity) {
      return MissingSearchType.DEPARTURE_AND_ARRIVAL;
    } else if (!validDepartureCity && !validTime) {
      return MissingSearchType.DEPARTURE_AND_TIME;
    } else if (!validArrivalCity && !validTime) {
      return MissingSearchType.ARRIVAL_AND_TIME;
    } else if (!validDepartureCity) {
      return MissingSearchType.DEPARTURE;
    } else if (!validArrivalCity) {
      return MissingSearchType.ARRIVAL;
    } else if (!validTime) {
      return MissingSearchType.TIME;
    } else  {
      return MissingSearchType.NONE;
    }
  }

}
