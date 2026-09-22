package com.klu.Service;

import com.klu.Model.Entity;
import com.klu.Repo.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PackageService {

    @Autowired
    private PackageRepo packageRepository;

    public Entity createPackage(Entity pkg) {
        pkg.setTrackingNumber(
            "TRK-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase()
        );

        pkg.setStatus("CREATED");

        return packageRepository.save(pkg);
    }

    public List<Entity> getAllPackages() {
        return packageRepository.findAll();
    }

    public Entity getPackageByTrackingNumber(String trackingNumber) {
        return packageRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Package not found with tracking number: "
                        + trackingNumber
                    )
                );
    }

    public Entity updateStatus(String trackingNumber, String status) {
        Entity pkg = getPackageByTrackingNumber(trackingNumber);
        pkg.setStatus(status);

        return packageRepository.save(pkg);
    }
}
