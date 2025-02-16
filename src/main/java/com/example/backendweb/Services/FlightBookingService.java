package com.example.backendweb.Services;

import com.example.backendweb.Entity.Booking.FlightBooking;
import com.example.backendweb.Repository.Booking.FlightBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class FlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    public List<FlightBooking> getAllFlightBookings() {
        return flightBookingRepository.findAll();
    }

    public void deleteFlightBooking(Integer flightBookingId) {
        flightBookingRepository.deleteById(flightBookingId);
    }

    public FlightBooking updateFlightBooking(Integer flightBookingId, FlightBooking flightBooking) {
        FlightBooking existingBooking = flightBookingRepository.findById(flightBookingId)
                .orElseThrow(() -> new EntityNotFoundException("Flight booking not found"));

        existingBooking.setFlightId(flightBooking.getFlightId());
        existingBooking.setSeatClass(flightBooking.getSeatClass());
        existingBooking.setPassengerName(flightBooking.getPassengerName());
        existingBooking.setPassengerId(flightBooking.getPassengerId());

        return flightBookingRepository.save(existingBooking);
    }

}