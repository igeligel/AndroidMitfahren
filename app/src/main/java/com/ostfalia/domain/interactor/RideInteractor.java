package com.ostfalia.domain.interactor;

import com.ostfalia.data.entity.Ride;
import com.ostfalia.data.repository.RidesRepository;
import com.ostfalia.domain.models.CreateRideModel;
import com.ostfalia.domain.models.SearchRideModel;
import com.ostfalia.domain.validator.CreateRideValidator;
import com.ostfalia.domain.validator.SearchRideValidator;
import com.ostfalia.domain.models.*;

import java.util.ArrayList;

/**
 * Class that contains the the interaction with the repository pattern
 */
public final class RideInteractor {

  /**
   * Inserts a new ride in the database, if the given model is valid
   * @param createRideModel possible input values for the repository
   */
  public static MissingSearchType createRide(CreateRideModel createRideModel) {
    // If not valid immediately return the missing type.
    MissingSearchType missingType = CreateRideValidator.IsModelValid(createRideModel);
    if (missingType != MissingSearchType.NONE) {
      return missingType;
    }

    Ride ride = new Ride();
    ride.Description = createRideModel.Description;
    ride.DepartureCity = createRideModel.DepartureCity;
    ride.ArrivalCity = createRideModel.ArrivalCity;
    ride.ArrivalTimestamp = createRideModel.ArrivalCalendar.getTimeInMillis() / 1000;
    ride.DepartureTimestamp = createRideModel.DepartureCalendar.getTimeInMillis() / 1000;
    RidesRepository.InsertOrUpdateRide(ride);
    return MissingSearchType.NONE;
  }

  /**
   * Search for possible rides for the given model in the repository, if the given model is valid
   * @param searchRideModel possible input for searching in the repository
   * @return list of possible rides
   */
  public static ArrayList<Ride> getRides(SearchRideModel searchRideModel) {

    if (!SearchRideValidator.IsModelValid(searchRideModel)) {
      return new ArrayList<>();
    }
    long departureTime = searchRideModel.Calendar.getTimeInMillis() / 1000;
    ArrayList<Ride> rides = RidesRepository.SearchRides(searchRideModel.DepartureCity,
      searchRideModel.ArrivalCity,
      departureTime,
      getSearchTypeBySearchRideModel(searchRideModel));
    return rides;
  }

  /**
   * Differentiate the given input in Searchtypes
   * @param searchRideModel model to search in the repository
   * @return searchtype for the repository
   */
  private static SearchType getSearchTypeBySearchRideModel(SearchRideModel searchRideModel) {
    if (searchRideModel.ArrivalCity.length() == 0 && searchRideModel.DepartureCity.length() == 0) {
      return SearchType.NO_CITY;
    }
    if (searchRideModel.ArrivalCity.length() == 0) {
      return SearchType.JUST_DEPARTURE_CITY;
    }
    if (searchRideModel.DepartureCity.length() == 0) {
      return SearchType.JUST_ARRIVAL_CITY;
    }
    return SearchType.BOTH_CITIES;

  }
}
