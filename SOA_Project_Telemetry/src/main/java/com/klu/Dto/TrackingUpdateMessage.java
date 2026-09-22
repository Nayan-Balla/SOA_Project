package com.klu.Dto;

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
    private String status;
    private String message;
}