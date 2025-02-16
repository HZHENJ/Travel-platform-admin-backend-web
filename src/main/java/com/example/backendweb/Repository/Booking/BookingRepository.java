package com.example.backendweb.Repository.Booking;

import com.example.backendweb.Entity.Booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT MONTH(b.updatedAt) as month, COUNT(b) as count " +
            "FROM Booking b " +
            "GROUP BY MONTH(b.updatedAt) " +
            "ORDER BY MONTH(b.updatedAt)")
    List<Object[]> getYearlyBookingCounts();

    @Query("SELECT b.bookingType, COUNT(b) FROM Booking b GROUP BY b.bookingType")
    List<Object[]> getBookingCountsByType();
}
