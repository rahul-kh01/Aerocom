package com.ac.in.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ac.in.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestinationAndDeparture(String origin, String destination, String departure);
}


