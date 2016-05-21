package com.ostfalia.domain.validator;

import com.ostfalia.domain.models.SearchRideModel;

/**
 * Validator for the search of rides
 */
public final class SearchRideValidator {
  /**
   * Validates the given model
   * @param searchRideModel given Model
   * @return true everytime, cause every search option can be handled by the database
   */
  public static boolean IsModelValid(SearchRideModel searchRideModel) {
    return true;
  }
}
