package com.klu.Repo;

import com.klu.Model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepo extends JpaRepository<Entity, Long> {

    Optional<Entity> findByTrackingNumber(String trackingNumber);
}
