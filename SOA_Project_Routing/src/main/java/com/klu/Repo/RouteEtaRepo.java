package com.klu.Repo;

import com.klu.Model.RouteEta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteEtaRepo extends JpaRepository<RouteEta, Long> {
    Optional<RouteEta> findByTrackingNumber(String trackingNumber);
}