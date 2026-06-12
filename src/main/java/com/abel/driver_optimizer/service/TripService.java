package com.abel.driver_optimizer.service;

import com.abel.driver_optimizer.model.Trip;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    private List<Trip> trips = new ArrayList<>();

    public Trip addTrip(Trip trip) {
        trips.add(trip);
        return trip;
    }

    public List<Trip> getAllTrips() {
        return trips;
    }

    public double calculateEarningsPerMile(Trip trip) {
        if (trip.getDistanceMiles() == 0) return 0;
        return trip.getEstimatedEarnings() / trip.getDistanceMiles();
    }

    public Trip getBestTrip() {
        return trips.stream()
                .max((t1, t2) -> Double.compare(
                        calculateEarningsPerMile(t1),
                        calculateEarningsPerMile(t2)))
                .orElse(null);
    }
}