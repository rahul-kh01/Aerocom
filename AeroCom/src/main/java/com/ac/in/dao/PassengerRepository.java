package com.ac.in.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ac.in.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
	
	 Passenger findByMobNoAndPassword(String mobNo, String password);
	 
	 Passenger findByMobNo(String mobNo);
}

