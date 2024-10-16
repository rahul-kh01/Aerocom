package com.ac.in.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ac.in.dao.PassengerRepository;
import com.ac.in.entity.Passenger;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;
    
    public Passenger login(String mobNo, String password) {
        
        return passengerRepository.findByMobNoAndPassword(mobNo, password);
    }

    public Passenger findByMobNo(String mobNo) {
        return passengerRepository.findByMobNo(mobNo);
    }
    public void savePassenger(Passenger passenger) {

        passengerRepository.save(passenger);
    }
    
}

