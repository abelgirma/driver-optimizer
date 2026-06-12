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
}