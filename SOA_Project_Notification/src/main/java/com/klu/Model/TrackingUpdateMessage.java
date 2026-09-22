package com.klu.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingUpdateMessage {
    private String trackingNumber;
    private double currentLatitude;
    private double currentLongitude;
    private double remainingDistanceKm;
    private int estimatedMinutes;
    private String status; // e.g., "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED"
    private String message;
}