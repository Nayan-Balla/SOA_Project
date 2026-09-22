package com.klu.Service;

import com.klu.Model.TrackingUpdateMessage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    // Thread-safe in-memory storage to hold the latest notification per tracking number
    private final Map<String, TrackingUpdateMessage> latestNotifications = new ConcurrentHashMap<>();

    public TrackingUpdateMessage saveNotification(TrackingUpdateMessage update) {
        latestNotifications.put(update.getTrackingNumber(), update);
        return update;
    }

    public TrackingUpdateMessage getLatestNotification(String trackingNumber) {
        return latestNotifications.get(trackingNumber);
    }
}