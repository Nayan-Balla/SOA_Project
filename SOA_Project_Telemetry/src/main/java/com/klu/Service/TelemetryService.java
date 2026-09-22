package com.klu.Service;

import com.klu.Model.TelemetryData;
import com.klu.Repo.TelemetryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelemetryService {

    @Autowired
    private TelemetryRepo telemetryRepo;

    public TelemetryData logLocation(TelemetryData data) {
        if (data.getTimestamp() == null) {
            data.setTimestamp(LocalDateTime.now());
        }
        return telemetryRepo.save(data);
    }

    public List<TelemetryData> getLocationHistory(String trackingNumber) {
        return telemetryRepo.findByTrackingNumberOrderByTimestampDesc(trackingNumber);
    }

    public TelemetryData getLatestLocation(String trackingNumber) {
        return telemetryRepo.findFirstByTrackingNumberOrderByTimestampDesc(trackingNumber)
                .orElseThrow(() -> new RuntimeException("No telemetry data found for tracking number: " + trackingNumber));
    }
}