package com.abel.driver_optimizer;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private List<Trip> trips = new ArrayList<>();

    @GetMapping("/status")
    public String getStatus() {
        return "Driver Optimizer API is running!";
    }

    @PostMapping("/trip")
    public Trip addTrip(@RequestBody Trip trip) {
        trips.add(trip);
        return trip;
    }

    @GetMapping("/trips")
    public List<Trip> getAllTrips() {
        return trips;
    }
}