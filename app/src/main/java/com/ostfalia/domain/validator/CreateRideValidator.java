package com.ostfalia.domain.validator;

import com.ostfalia.domain.models.CreateRideModel;
import com.ostfalia.domain.models.MissingSearchType;
import com.ostfalia.presentation.presenter.ToastPresenter;

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
  public static MissingSearchType IsModelValid(CreateRideModel createRideModel) {

    if (createRideModel.DepartureCity.length() == 0) {
      return MissingSearchType.DEPARTURE;
    }
    if (createRideModel.ArrivalCity.length() == 0) {
      return MissingSearchType.ARRIVAL;
    }
    if (createRideModel.DepartureCalendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
      return MissingSearchType.DEPARTURE_DATE;
    }
    if (createRideModel.ArrivalCalendar.getTimeInMillis() < createRideModel.DepartureCalendar.getTimeInMillis()) {
      return MissingSearchType.ARRIVAL_DATE;
    }
    if (createRideModel.Description.length() == 0) {
      return MissingSearchType.DESCRIPTION;
    }
    return MissingSearchType.NONE;
  }
}
