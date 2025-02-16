package com.example.backendweb.Repository.Booking;

import com.example.backendweb.Entity.Booking.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Integer> {
    // Add custom queries if needed
}