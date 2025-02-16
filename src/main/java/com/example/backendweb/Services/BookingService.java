package com.example.backendweb.Services;

import com.example.backendweb.Entity.Booking.Booking;
import com.example.backendweb.Repository.Booking.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Integer bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            // 2. Delete the booking
            bookingRepository.delete(booking);

        } else {
            throw new EntityNotFoundException("Booking not found with ID: " + bookingId);
        }
    }

    public Booking updateBooking(Integer bookingId, Booking booking) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        // Update the fields you want to allow updating.  Don't just set the whole object!
        existingBooking.setUserId(booking.getUserId());
        existingBooking.setBookingType(booking.getBookingType());
        existingBooking.setStatus(booking.getStatus());
        existingBooking.setTotalAmount(booking.getTotalAmount());
        existingBooking.setUpdatedAt(LocalDateTime.now());
        // ... update other fields ...

        return bookingRepository.save(existingBooking);
    }

}