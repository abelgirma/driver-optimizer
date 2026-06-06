package com.abel.driver_optimizer;

import lombok.Data;

@Data
public class Trip {
    private String tripId;
    private String pickupLocation;
    private String dropoffLocation;
    private double distanceMiles;
    private double estimatedEarnings;
    private int durationMinutes;
}