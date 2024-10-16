package com.ac.in.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ac.in.entity.Ticket;

import jakarta.transaction.Transactional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	List<Ticket> findByMobileNumber(String mobNo);
	
	@Transactional
	void deleteByTicketNumber(String ticketNumber);
	
}

