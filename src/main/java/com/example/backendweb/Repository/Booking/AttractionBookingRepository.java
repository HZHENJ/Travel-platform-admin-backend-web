package com.example.backendweb.Repository.Booking;

import com.example.backendweb.Entity.Booking.AttractionBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AttractionBookingRepository extends JpaRepository<AttractionBooking, Integer> {
    // You can add custom queries here if needed, for example:
    // List<AttractionBooking> findByAttractionId(Integer attractionId);
    // List<AttractionBooking> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);
    // ... other custom queries ...

    // Most Frequent Attractions
    @Query("SELECT a.attractionName, COUNT(ab) " +
            "FROM AttractionBooking ab " +
            "JOIN ab.attraction a " +  // Assuming you have a relationship between AttractionBooking and Attraction
            "GROUP BY a.attractionName " +
            "ORDER BY COUNT(ab) DESC")
    List<Object[]> getMostFrequentAttractions();

    @Query(value = """
            SELECT a.attraction_id as attractionId, 
                   a.attraction_name as attractionName,
                   COUNT(ab.attraction_booking_id) as bookingCount 
            FROM attractions a 
            LEFT JOIN attraction_bookings ab ON a.attraction_id = ab.attraction_id 
            GROUP BY a.attraction_id, a.attraction_name 
            ORDER BY COUNT(ab.attraction_booking_id) DESC
            """, nativeQuery = true)
    List<Map<String, Object>> findAttractionsOrderedByBookingCount();
}
