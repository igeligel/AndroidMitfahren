package com.leon.domain.interactor;

import android.util.Log;

import com.leon.data.repository.RidesRepository;
import com.leon.domain.CreateRideModel;
import com.leon.data.entity.*;

/**
 * Created by Kevin on 05/06/2016.
 */
public final class SetRide {
  public static void createRide(CreateRideModel createRideModel) {
    // TODO: Database!
    Ride ride = new Ride();
    ride.Description = createRideModel.Description;
    ride.DepartureCity = createRideModel.DepartureCity;
    ride.ArrivalCity = createRideModel.ArrivalCity;
    ride.ArrivalTimestamp = createRideModel.ArrivalCalendar.getTimeInMillis() / 1000;
    ride.DepartureTimestamp = createRideModel.DepartureCalendar.getTimeInMillis() / 1000;
    RidesRepository.InsertOrUpdateRide(ride);
  }
}
