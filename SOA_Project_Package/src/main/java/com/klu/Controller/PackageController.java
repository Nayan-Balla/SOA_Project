package com.klu.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.Model.Entity;
import com.klu.Service.PackageService;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping
    public ResponseEntity<Entity> createPackage(@RequestBody Entity pkg) {
        return ResponseEntity.ok(packageService.createPackage(pkg));
    }

    @GetMapping
    public ResponseEntity<List<Entity>> getAllPackages() {
        return ResponseEntity.ok(packageService.getAllPackages());
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<Entity> getPackageByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(packageService.getPackageByTrackingNumber(trackingNumber));
    }

    @PutMapping("/{trackingNumber}/status")
    public ResponseEntity<Entity> updateStatus(@PathVariable String trackingNumber, @RequestParam String status) {
        return ResponseEntity.ok(packageService.updateStatus(trackingNumber, status));
    }
}