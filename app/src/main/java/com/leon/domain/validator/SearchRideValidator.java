package com.leon.domain.validator;

import com.leon.domain.SearchRideModel;

/**
 * Created by Kevin on 05/07/2016.
 */
public final class SearchRideValidator {
  public static boolean IsModelValid(SearchRideModel createRideModel) {
    if (createRideModel.ArrivalCity.length() == 0) {
      return false;
    }
    if (createRideModel.DepartureCity.length() == 0) {
      return false;
    }
    return true;
  }
}
