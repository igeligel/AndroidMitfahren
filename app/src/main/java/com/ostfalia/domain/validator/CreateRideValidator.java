package com.ostfalia.domain.validator;

import com.ostfalia.domain.models.CreateRideModel;

import java.util.Calendar;

/**
 * Created by Kevin on 05/06/2016.
 */
public final class CreateRideValidator {
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
