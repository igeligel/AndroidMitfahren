package com.leon.domain.interactor;

import com.leon.data.entity.Ride;
import com.leon.data.repository.RidesRepository;
import com.leon.domain.CreateRideModel;
import com.leon.domain.SearchRideModel;
import com.leon.domain.validator.CreateRideValidator;
import com.leon.domain.validator.SearchRideValidator;
import com.leon.domain.models.*;

import java.util.ArrayList;

/**
 * Created by Kevin on 05/06/2016.
 */
public final class RideInteractor {
  public static void createRide(CreateRideModel createRideModel) {
    // If not valid immediately return.
    if (!CreateRideValidator.IsModelValid(createRideModel)) {
      return;
    }

    Ride ride = new Ride();
    ride.Description = createRideModel.Description;
    ride.DepartureCity = createRideModel.DepartureCity;
    ride.ArrivalCity = createRideModel.ArrivalCity;
    ride.ArrivalTimestamp = createRideModel.ArrivalCalendar.getTimeInMillis() / 1000;
    ride.DepartureTimestamp = createRideModel.DepartureCalendar.getTimeInMillis() / 1000;
    RidesRepository.InsertOrUpdateRide(ride);
  }

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

  private static SearchType getSearchTypeBySearchRideModel(SearchRideModel searchRideModel) {
    if (searchRideModel.ArrivalCity.length() == 0 && searchRideModel.DepartureCity.length() == 0) {
      return SearchType.NoCity;
    }
    if (searchRideModel.ArrivalCity.length() == 0) {
      return SearchType.JustDepartueCity;
    }
    if (searchRideModel.DepartureCity.length() == 0) {
      return SearchType.JustArrivalCity;
    }
    return SearchType.BothCities;

  }
}
