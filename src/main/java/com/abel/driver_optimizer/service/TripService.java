package com.abel.driver_optimizer.service;

import com.abel.driver_optimizer.model.Trip;
import com.abel.driver_optimizer.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public Trip addTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip updateTrip(String tripId, Trip updatedTrip) {
        return tripRepository.findById(tripId).map(existingTrip -> {
            existingTrip.setPickupLocation(updatedTrip.getPickupLocation());
            existingTrip.setDropoffLocation(updatedTrip.getDropoffLocation());
            existingTrip.setDistanceMiles(updatedTrip.getDistanceMiles());
            existingTrip.setEstimatedEarnings(updatedTrip.getEstimatedEarnings());
            existingTrip.setDurationMinutes(updatedTrip.getDurationMinutes());
            return tripRepository.save(existingTrip);
        }).orElse(null);
    }

    public void deleteTrip(String tripId) {
        tripRepository.deleteById(tripId);
    }

    public double calculateEarningsPerMile(Trip trip) {
        if (trip.getDistanceMiles() == 0) return 0;
        return trip.getEstimatedEarnings() / trip.getDistanceMiles();
    }

    public double calculateEarningsPerHour(Trip trip) {
        if (trip.getDurationMinutes() == 0) return 0;
        return (trip.getEstimatedEarnings() / trip.getDurationMinutes()) * 60;
    }

    public double calculateEfficiencyScore(Trip trip) {
        double earningsPerMile = calculateEarningsPerMile(trip);
        double earningsPerHour = calculateEarningsPerHour(trip);
        return (earningsPerMile * 0.4) + (earningsPerHour * 0.6);
    }

    public Trip getBestTrip() {
        return tripRepository.findAll().stream()
                .max((t1, t2) -> Double.compare(
                        calculateEfficiencyScore(t1),
                        calculateEfficiencyScore(t2)))
                .orElse(null);
    }
}