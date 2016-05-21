package com.ostfalia.domain.validator;

import com.ostfalia.domain.models.CreateRideModel;

import java.util.Calendar;

/**
 * Validator for the CreateRideModel
 */
public final class CreateRideValidator {
  /**
   * Checks if the given model is valid to create a new ride in the Database
   * @param createRideModel given input model
   * @return true, when the model is valid, else it returns false
   */
  public static boolean IsModelValid(CreateRideModel createRideModel) {
    if (createRideModel.Description.length() == 0) {
      return false;
    }
    if (createRideModel.ArrivalCity.length() == 0) {
      return false;
    }
    if (createRideModel.DepartureCity.length() == 0) {
      return false;
    }
    if (createRideModel.ArrivalCalendar.getTimeInMillis() < createRideModel.DepartureCalendar.getTimeInMillis()) {
      return false;
    }
    if (createRideModel.DepartureCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
      return false;
    }
    return true;
  }
}
