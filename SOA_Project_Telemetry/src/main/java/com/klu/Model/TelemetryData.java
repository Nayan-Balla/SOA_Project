package com.klu.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courierId;
    private String trackingNumber;

    private double latitude;
    private double longitude;
    private double speed; // in km/h

    private LocalDateTime timestamp;
}