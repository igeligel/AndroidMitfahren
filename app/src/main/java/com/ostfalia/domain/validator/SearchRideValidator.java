package com.ostfalia.domain.validator;

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
   * @return true everytime, cause every search option can be handled by the database
   */
  public static MissingSearchType IsModelValid(SearchRideModel searchRideModel) {
    boolean validDepartureCity = true;
    boolean validArrivalCity = true;
    boolean validTime = true;
    MissingSearchType missingType;

    if(searchRideModel.DepartureCity.length() == 0) {
      validDepartureCity = false;
    }
    if(searchRideModel.ArrivalCity.length() == 0) {
      validArrivalCity = false;
    }
    if(searchRideModel.Calendar.getTimeInMillis()
            >= Calendar.getInstance().getTimeInMillis() &&
            searchRideModel.Calendar.getTimeInMillis()
                    < Calendar.getInstance().getTimeInMillis() + 1000) {
      validTime = false;
    }

    if (!validDepartureCity && !validArrivalCity && !validTime) {
      missingType = MissingSearchType.ALL;
    } else if (!validDepartureCity && !validArrivalCity) {
      missingType = MissingSearchType.DEPARTURE_AND_ARRIVAL;
    } else if (!validDepartureCity && !validTime) {
      missingType = MissingSearchType.DEPARTURE_AND_TIME;
    } else if (!validArrivalCity && !validTime) {
      missingType = MissingSearchType.ARRIVAL_AND_TIME;
    } else if (!validDepartureCity) {
      missingType = MissingSearchType.DEPARTURE;
    } else if (!validArrivalCity) {
      missingType = MissingSearchType.ARRIVAL;
    } else if (!validTime) {
      missingType = MissingSearchType.TIME;
    } else  {
      missingType = MissingSearchType.NONE;
    }
    
    return missingType;
  }

}
