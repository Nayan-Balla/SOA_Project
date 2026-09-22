package com.klu.Service;

import com.klu.Model.RouteEta;
import com.klu.Repo.RouteEtaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RouteEtaService {

    @Autowired
    private RouteEtaRepo routeEtaRepo;

    public RouteEta calculateAndSaveEta(String trackingNumber, double currentLat, double currentLng, double destLat, double destLng, double speedKmH) {
        double distanceKm = calculateHaversineDistance(currentLat, currentLng, destLat, destLng);
        
        // Default speed assumption to 40 km/h if speed is 0 or stopped
        double effectiveSpeed = (speedKmH > 5) ? speedKmH : 40.0;
        int etaMinutes = (int) Math.round((distanceKm / effectiveSpeed) * 60);

        RouteEta routeEta = routeEtaRepo.findByTrackingNumber(trackingNumber)
                .orElse(new RouteEta());

        routeEta.setTrackingNumber(trackingNumber);
        routeEta.setCurrentLatitude(currentLat);
        routeEta.setCurrentLongitude(currentLng);
        routeEta.setDestinationLatitude(destLat);
        routeEta.setDestinationLongitude(destLng);
        routeEta.setRemainingDistanceKm(Math.round(distanceKm * 100.0) / 100.0);
        routeEta.setEstimatedMinutes(etaMinutes);
        routeEta.setLastUpdated(LocalDateTime.now());

        return routeEtaRepo.save(routeEta);
    }

    public RouteEta getEta(String trackingNumber) {
        return routeEtaRepo.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("ETA record not found for tracking number: " + trackingNumber));
    }

    // Haversine formula to compute geographical distance between two GPS points
    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}