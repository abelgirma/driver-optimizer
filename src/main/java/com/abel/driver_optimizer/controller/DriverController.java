package com.abel.driver_optimizer.controller;

import com.abel.driver_optimizer.model.Trip;
import com.abel.driver_optimizer.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private TripService tripService;

    @GetMapping("/status")
    public String getStatus() {
        return "Driver Optimizer API is running!";
    }

    @PostMapping("/trip")
    public Trip addTrip(@RequestBody Trip trip) {
        return tripService.addTrip(trip);
    }

    @GetMapping("/trips")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/trips/best")
    public Trip getBestTrip() {
        return tripService.getBestTrip();
    }

    @PutMapping("/trip/{tripId}")
    public Trip updateTrip(@PathVariable String tripId, @RequestBody Trip trip) {
        return tripService.updateTrip(tripId, trip);
    }

    @DeleteMapping("/trip/{tripId}")
    public String deleteTrip(@PathVariable String tripId) {
        tripService.deleteTrip(tripId);
        return "Trip " + tripId + " deleted successfully";
    }

    @GetMapping("/trips/scores")
    public List<java.util.Map<String, Object>> getTripScores() {
        return tripService.getAllTrips().stream()
                .map(trip -> {
                    java.util.Map<String, Object> score = new java.util.LinkedHashMap<>();
                    score.put("tripId", trip.getTripId());
                    score.put("pickupLocation", trip.getPickupLocation());
                    score.put("dropoffLocation", trip.getDropoffLocation());
                    score.put("earningsPerMile",
                            Math.round(tripService.calculateEarningsPerMile(trip) * 100.0) / 100.0);
                    score.put("earningsPerHour",
                            Math.round(tripService.calculateEarningsPerHour(trip) * 100.0) / 100.0);
                    score.put("efficiencyScore",
                            Math.round(tripService.calculateEfficiencyScore(trip) * 100.0) / 100.0);
                    return score;
                })
                .sorted((a, b) -> Double.compare(
                        (double) b.get("efficiencyScore"),
                        (double) a.get("efficiencyScore")))
                .collect(java.util.stream.Collectors.toList());
    }
}