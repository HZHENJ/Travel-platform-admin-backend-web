package com.example.backendweb.Repository.Booking;

import com.example.backendweb.Entity.Booking.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Integer> {

}