package com.klu.Repo;

import com.klu.Model.TelemetryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetryRepo extends JpaRepository<TelemetryData, Long> {
    
    // Get all coordinates logged for a package
    List<TelemetryData> findByTrackingNumberOrderByTimestampDesc(String trackingNumber);

    // Get the latest single location update for a package
    Optional<TelemetryData> findFirstByTrackingNumberOrderByTimestampDesc(String trackingNumber);
}