package com.ostfalia.domain.validator;

import com.ostfalia.domain.models.CreateRideModel;
import com.ostfalia.domain.models.MissingCreateType;

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
  public static MissingCreateType IsModelValid(CreateRideModel createRideModel) {

    if (createRideModel.DepartureCity.length() == 0) {
      return MissingCreateType.DEPARTURE;
    }
    if (createRideModel.ArrivalCity.length() == 0) {
      return MissingCreateType.ARRIVAL;
    }
    if (createRideModel.DepartureCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
      return MissingCreateType.DEPARTURE_DATE;
    }
    if (createRideModel.ArrivalCalendar.getTimeInMillis() < createRideModel.DepartureCalendar.getTimeInMillis()) {
      return MissingCreateType.ARRIVAL_DATE;
    }
    if (createRideModel.Description.length() == 0) {
      return MissingCreateType.DESCRIPTION;
    }
    return MissingCreateType.NONE;
  }
}
