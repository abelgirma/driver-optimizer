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

    public double calculateEarningsPerMile(Trip trip) {
        if (trip.getDistanceMiles() == 0) return 0;
        return trip.getEstimatedEarnings() / trip.getDistanceMiles();
    }

    public Trip getBestTrip() {
        return tripRepository.findAll().stream()
                .max((t1, t2) -> Double.compare(
                        calculateEarningsPerMile(t1),
                        calculateEarningsPerMile(t2)))
                .orElse(null);
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
}