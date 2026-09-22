package com.klu.Controller;

import com.klu.Model.TrackingUpdateMessage;
import com.klu.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/update")
    public ResponseEntity<TrackingUpdateMessage> sendNotification(@RequestBody TrackingUpdateMessage update) {
        TrackingUpdateMessage saved = notificationService.saveNotification(update);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<?> getNotification(@PathVariable String trackingNumber) {
        TrackingUpdateMessage update = notificationService.getLatestNotification(trackingNumber);
        
        if (update == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No notification found for tracking number: " + trackingNumber);
        }
        
        return ResponseEntity.ok(update);
    }
}