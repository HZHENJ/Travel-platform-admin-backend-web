package com.example.backendweb.Services;

import com.example.backendweb.Entity.Booking.AttractionBooking;
import com.example.backendweb.Repository.Booking.AttractionBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttractionBookingService {

    @Autowired
    private AttractionBookingRepository attractionBookingRepository;

    public List<AttractionBooking> getAllAttractionBookings() {
        return attractionBookingRepository.findAll();
    }

    public AttractionBooking updateAttractionBooking(Integer attractionBookingId, AttractionBooking attractionBooking) {
        AttractionBooking existingBooking = attractionBookingRepository.findById(attractionBookingId)
                .orElseThrow(() -> new EntityNotFoundException("Attraction booking not found"));

        existingBooking.setBookingId(attractionBooking.getBookingId());
        existingBooking.setAttractionId(attractionBooking.getAttractionId());
        existingBooking.setVisitDate(attractionBooking.getVisitDate());
        existingBooking.setTicketType(attractionBooking.getTicketType());
        existingBooking.setNumberOfTickets(attractionBooking.getNumberOfTickets());

        return attractionBookingRepository.save(existingBooking);
    }

    public void deleteAttractionBooking(Integer attractionBookingId) {
        attractionBookingRepository.deleteById(attractionBookingId);
    }

    public List<Map<String, Object>> getAttractionsOrderedByBookingCount() {
        return attractionBookingRepository.findAttractionsOrderedByBookingCount();
    }
}
