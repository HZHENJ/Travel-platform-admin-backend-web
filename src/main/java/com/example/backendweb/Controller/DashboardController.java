package com.example.backendweb.Controller;

import com.example.backendweb.Repository.Booking.AttractionBookingRepository;
import com.example.backendweb.Repository.Booking.BookingRepository;
import com.example.backendweb.Entity.Booking.Booking;
import com.example.backendweb.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AttractionBookingRepository attractionBookingRepository;

    @Autowired
    private JwtService jwtService;

    private boolean isAuthorized(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtService.validateToken(token);
        }
        return false;
    }

    @GetMapping("/yearlyBookings")
    public ResponseEntity<?> getYearlyBookings(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Map<String, Object>> yearlyBookings = new ArrayList<>();
        List<Object[]> results = bookingRepository.getYearlyBookingCounts();
        for (Object[] result : results) {
            int monthNumber = ((Number) result[0]).intValue();
            Month month = Month.of(monthNumber);
            int count = ((Number) result[1]).intValue();
            yearlyBookings.add(createBookingData(month.toString(), count));
        }
        return ResponseEntity.ok(yearlyBookings);
    }

    @GetMapping("/bookingsByType")
    public ResponseEntity<?> getBookingsByType(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Map<String, Object>> bookingsByType = new ArrayList<>();
        List<Object[]> results = bookingRepository.getBookingCountsByType();
        for (Object[] result : results) {
            Booking.BookingType type = (Booking.BookingType) result[0];
            long count = ((Number) result[1]).longValue();
            bookingsByType.add(createBookingTypeData(type.toString(), (int) count));
        }
        return ResponseEntity.ok(bookingsByType);
    }

    @GetMapping("/mostFrequentPlaces")
    public ResponseEntity<?> getMostFrequentPlaces(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Map<String, Object>> mostFrequentPlaces = new ArrayList<>();
        List<Object[]> results = attractionBookingRepository.getMostFrequentAttractions();
        for (Object[] result : results) {
            String place = (String) result[0];
            long visits = ((Number) result[1]).longValue();
            mostFrequentPlaces.add(createFrequentPlaceData(place, (int) visits));
        }
        return ResponseEntity.ok(mostFrequentPlaces);
    }

    @GetMapping("/visitorSatisfaction")
    public ResponseEntity<?> getVisitorSatisfaction(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Map<String, Object>> visitorSatisfaction = new ArrayList<>();
        visitorSatisfaction.add(createSatisfactionData("Very Satisfied", 80));
        visitorSatisfaction.add(createSatisfactionData("Satisfied", 15));
        visitorSatisfaction.add(createSatisfactionData("Neutral", 5));
        return ResponseEntity.ok(visitorSatisfaction);
    }

    private Map<String, Object> createBookingData(String month, int count) {
        Map<String, Object> data = new HashMap<>();
        data.put("month", month);
        data.put("count", count);
        return data;
    }

    private Map<String, Object> createBookingTypeData(String type, int count) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("count", count);
        return data;
    }

    private Map<String, Object> createFrequentPlaceData(String place, int visits) {
        Map<String, Object> data = new HashMap<>();
        data.put("place", place);
        data.put("visits", visits);
        return data;
    }

    private Map<String, Object> createSatisfactionData(String satisfaction, int count) {
        Map<String, Object> data = new HashMap<>();
        data.put("satisfaction", satisfaction);
        data.put("count", count);
        return data;
    }
}
