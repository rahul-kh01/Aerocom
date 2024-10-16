package com.ac.in.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ac.in.entity.Flight;
import com.ac.in.entity.Passenger;
import com.ac.in.entity.Ticket;
import com.ac.in.service.FlightService;
import com.ac.in.service.PassengerService;
import com.ac.in.service.TicketService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("loggedInPassenger")
public class FlightController {
	
	 @Autowired
	 private PassengerService passengerService;
	 
	 @Autowired
	 private TicketService ticketService;
	 
	 @Autowired
	 private FlightService flightService;
	 
    @GetMapping("/")
    public String home() {
        return "home"; 
    }

    @GetMapping("/search-flights")
    public String searchFlights(@RequestParam("origin") String origin,
                                @RequestParam("destination") String destination,
                                @RequestParam("departure") String departure,
                                Model model) {
    	
    	List<Flight> flights = flightService.searchFlights(origin, destination, departure);
        if (flights.isEmpty()) {
            model.addAttribute("message", "No Flight Available For Given Date.");
        } else {
            model.addAttribute("flights", flights);
        }
        return "search-results";  
    }
    
    @GetMapping("/search-results")
    public String searchResultForm() {
        return "search-results";
    }
    
    
    @GetMapping("/login2")
    public String showLoginForm() {
        return "login2";
    }

    @PostMapping("/login2")
    public String login(@RequestParam("mobNo") String mobNo,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {
    	
    	Passenger passenger = passengerService.login(mobNo, password);
        
        if (passenger != null) {
            session.setAttribute("loggedInPassengerName", passenger.getName());
            session.setAttribute("loggedInPassengerMobNo", passenger.getMobNo());
            session.setAttribute("loggedInPassengerGender", passenger.getGender());
            session.setAttribute("loggedInPassengerEmail", passenger.getEmail());
            session.setAttribute("loggedInPassengerAge", passenger.getAge());
            session.setAttribute("loggedInPassenger", passenger);
            return "redirect:/user3"; }
            else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login2";  
        }
    }


    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "signup";  
    }

    @PostMapping("/signup")
    public String createPassenger(@Valid @ModelAttribute Passenger passenger, BindingResult result) {
        if (result.hasErrors()) {
            return "signup";  
        }
        passengerService.savePassenger(passenger);
        return "redirect:/login2";  
    }
    
	 
    
    @GetMapping("/user3")
    public String showUserPage(Model model, HttpSession session) {
    	 String passengerName = (String) session.getAttribute("loggedInPassengerName");
    	 String passengerMobNo = (String) session.getAttribute("loggedInPassengerMobNo");
    	 String passengerGender = (String) session.getAttribute("loggedInPassengerGender");
    	 String passengerEmail = (String) session.getAttribute("loggedInPassengerEmail");
    	 String passengerAge =  (String) session.getAttribute("loggedInPassengerAge");

    	    if (passengerName == null) {
    	        return "redirect:/login2";
    	    }

    	    model.addAttribute("passengerName", passengerName);
    	    model.addAttribute("passengerMobNo", passengerMobNo);
    	    model.addAttribute("passengerGender", passengerGender);
    	    model.addAttribute("passengerEmail", passengerEmail);
    	    model.addAttribute("passengerAge", passengerAge);
    	    
    	    return "user3";  
    }

    @GetMapping("/search-flight3")
    public String showflightForm() {
        return "search-flight8";
    }
    @GetMapping("/search-flight")
    public String searchFlights2(@RequestParam("origin") String origin,
                                @RequestParam("destination") String destination,
                                @RequestParam("departure") String departure,
                                Model model) {
       List<Flight> flights = flightService.searchFlights(origin, destination, departure);
        if (flights.isEmpty()) {
            model.addAttribute("message", "No Flight Available For Given Date.");
        } else {
            model.addAttribute("flights", flights);
        }
        return "search-results2";  
    }
    
    @GetMapping("/search-results2")
    public String searchResult2Form() {
        return "search-results2";
    }
    
    @GetMapping("/book-ticket2")
    public String showBooking2Form() {
        return "book-ticket2";
    }
    
    @PostMapping("/book-ticket2")
    public String bookTicket2(@RequestParam("names") List<String> names,
                             @RequestParam("gender") List<String> gender,
                             @RequestParam("ages") List<Integer> ages,
                             @RequestParam("mobiles") List<String> mobiles,
                             @RequestParam("passengerTypes") List<String> passengerTypes,
                             @RequestParam("flightNumbers") List<String> flightNumbers,
                             @RequestParam("cabinClasses") List<String> cabinClasses,
                             Model model) {

        List<Ticket> tickets = new ArrayList<>();

        int numberOfPassengers = names.size();
        for (int i = 0; i < numberOfPassengers; i++) {
            Ticket ticket = new Ticket();
            
            ticket.setName(names.get(i));
            ticket.setGender(gender.get(i));  
            ticket.setAge(ages.get(i));
            ticket.setMobileNumber(mobiles.get(i));
            ticket.setPassengerType(passengerTypes.get(i));
            ticket.setFlightNumber(flightNumbers.get(i));
            ticket.setCabinClass(cabinClasses.get(i));
     
            ticketService.save(ticket);
            tickets.add(ticket);
        }

        model.addAttribute("message", "Tickets booked successfully!");
        model.addAttribute("tickets", tickets);
        return "success";  
    }
    
    @GetMapping("/book-ticket")
    public String showBookingForm() {
        return "book-ticket";
    }
    
    @PostMapping("/book-ticket")
    public String bookTicket(@RequestParam("names") List<String> names,
                             @RequestParam("gender") List<String> gender,
                             @RequestParam("ages") List<Integer> ages,
                             @RequestParam("mobiles") List<String> mobiles,
                             @RequestParam("passengerTypes") List<String> passengerTypes,
                             @RequestParam("flightNumbers") List<String> flightNumbers,
                             @RequestParam("cabinClasses") List<String> cabinClasses,
                             Model model) {

        List<Ticket> tickets = new ArrayList<>();

        int numberOfPassengers = names.size();
        for (int i = 0; i < numberOfPassengers; i++) {
            Ticket ticket = new Ticket();
            
            ticket.setName(names.get(i));
            ticket.setGender(gender.get(i));  
            ticket.setAge(ages.get(i));
            ticket.setMobileNumber(mobiles.get(i));
            ticket.setPassengerType(passengerTypes.get(i));
            ticket.setFlightNumber(flightNumbers.get(i));
            ticket.setCabinClass(cabinClasses.get(i));
     
            ticketService.save(ticket);
            tickets.add(ticket);
        }

        model.addAttribute("message", "Tickets booked successfully!");
        model.addAttribute("tickets", tickets);
        return "success";  
    }
    
    @GetMapping("/success")
    public String showSuccessForm() {
        return "success";
    }
    
    @GetMapping("/show-tickets")
    public String showMyBookings(Model model, HttpSession session) {
        String mobNo = (String) session.getAttribute("loggedInPassengerMobNo");

        if (mobNo == null) {
            return "redirect:/login2";
        }

        List<Ticket> tickets = ticketService.findAllByMobNo(mobNo);

        model.addAttribute("tickets", tickets);

        return "show-tickets";
    }
    
    @GetMapping("/customer-service")
    public String customerService() {
        return "customer-service";  
    }
    
    @GetMapping("/cancel-ticket/{ticketNumber}")
    public String cancelTicket(@PathVariable("ticketNumber") String ticketNumber, HttpSession session, Model model) {
        String mobNo = (String) session.getAttribute("loggedInPassengerMobNo");
        if (mobNo == null) {
            return "redirect:/login";
        }

        ticketService.cancelTicket(ticketNumber);

        return "redirect:/cancel-ticket";
    }

    @GetMapping("/cancel-ticket")
    public String showCancelTicketPage(HttpSession session, Model model) {
        String mobNo = (String) session.getAttribute("loggedInPassengerMobNo");
        if (mobNo == null) {
            return "redirect:/login";
        }

        List<Ticket> tickets = ticketService.findAllByMobNo(mobNo);
        model.addAttribute("tickets", tickets);

        return "cancel-ticket";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login2";
    }
    
}

