package com.klu.Controller;

import com.klu.Dto.TrackingUpdateMessage;
import com.klu.Model.TelemetryData;
import com.klu.Service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

    @Autowired
    private RestTemplate restTemplate;

    // Send GPS update from driver device AND trigger Notification Service via RestTemplate
    @PostMapping
    public ResponseEntity<TelemetryData> receiveTelemetry(@RequestBody TelemetryData data) {
        // 1. Execute your existing database log logic
        TelemetryData savedData = telemetryService.logLocation(data);

        // 2. Automatically notify NOTIFICATION-SERVICE via RestTemplate
        try {
            TrackingUpdateMessage updateMessage = new TrackingUpdateMessage(
                data.getTrackingNumber(),
                data.getLatitude(),
                data.getLongitude(),
                3.2,          // Remaining distance in km (sample calculation)
                8,            // Estimated minutes (sample calculation)
                "IN_TRANSIT",
                "Driver updated location via GPS stream"
            );

            String notificationUrl = "http://NOTIFICATION-SERVICE/api/notifications/update";
            restTemplate.postForEntity(notificationUrl, updateMessage, String.class);
        } catch (Exception e) {
            // Log error so that a notification failure doesn't stop telemetry saving
            System.err.println("Could not reach NOTIFICATION-SERVICE: " + e.getMessage());
        }

        return ResponseEntity.ok(savedData);
    }

    // Get all past coordinates recorded for a package (KEPT AS IS)
    @GetMapping("/{trackingNumber}/history")
    public ResponseEntity<List<TelemetryData>> getHistory(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(telemetryService.getLocationHistory(trackingNumber));
    }

    // Get current/latest location of a package (KEPT AS IS)
    @GetMapping("/{trackingNumber}/latest")
    public ResponseEntity<TelemetryData> getLatest(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(telemetryService.getLatestLocation(trackingNumber));
    }
}