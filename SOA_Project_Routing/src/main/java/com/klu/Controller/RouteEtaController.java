package com.klu.Controller;

import com.klu.Model.RouteEta;
import com.klu.Model.RouteEtaRequest;
import com.klu.Service.RouteEtaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routing")
public class RouteEtaController {

    @Autowired
    private RouteEtaService routeEtaService;

    // Calculate and save ETA using JSON request
    @PostMapping("/calculate")
    public ResponseEntity<RouteEta> calculateEta(
            @RequestBody RouteEtaRequest request) {

        return ResponseEntity.ok(
                routeEtaService.calculateAndSaveEta(
                        request.getTrackingNumber(),
                        request.getCurrentLat(),
                        request.getCurrentLng(),
                        request.getDestLat(),
                        request.getDestLng(),
                        request.getSpeed()
                )
        );
    }

    // Fetch computed ETA
    @GetMapping("/{trackingNumber}")
    public ResponseEntity<RouteEta> getEta(
            @PathVariable String trackingNumber) {

        return ResponseEntity.ok(
                routeEtaService.getEta(trackingNumber)
        );
    }
}