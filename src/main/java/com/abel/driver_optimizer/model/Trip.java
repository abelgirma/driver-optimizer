package com.abel.driver_optimizer.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tripId;

    private String pickupLocation;
    private String dropoffLocation;
    private double distanceMiles;
    private double estimatedEarnings;
    private int durationMinutes;
}