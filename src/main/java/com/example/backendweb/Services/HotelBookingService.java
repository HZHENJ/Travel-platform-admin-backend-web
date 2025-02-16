package com.example.backendweb.Services;

import com.example.backendweb.Entity.Booking.HotelBooking;
import com.example.backendweb.Repository.Booking.HotelBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelBookingService {

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    public List<HotelBooking> getAllHotelBookings() {
        return hotelBookingRepository.findAll();
    }

    public HotelBooking updateHotelBooking(Integer hotelBookingId, HotelBooking hotelBooking) {
        HotelBooking existingBooking = hotelBookingRepository.findById(hotelBookingId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel booking not found"));

        existingBooking.setHotelId(hotelBooking.getHotelId());
        existingBooking.setRoomType(hotelBooking.getRoomType());
        existingBooking.setLocation(hotelBooking.getLocation());
        existingBooking.setCheckInDate(hotelBooking.getCheckInDate());
        existingBooking.setCheckOutDate(hotelBooking.getCheckOutDate());
        existingBooking.setGuests(hotelBooking.getGuests());

        return hotelBookingRepository.save(existingBooking);
    }

    public void deleteHotelBooking(Integer hotelBookingId) {
        hotelBookingRepository.deleteById(hotelBookingId);
    }

}
