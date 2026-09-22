package com.klu.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_eta_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteEta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    private double currentLatitude;
    private double currentLongitude;
    
    private double destinationLatitude;
    private double destinationLongitude;

    private double remainingDistanceKm;
    private int estimatedMinutes;

    private LocalDateTime lastUpdated;
}