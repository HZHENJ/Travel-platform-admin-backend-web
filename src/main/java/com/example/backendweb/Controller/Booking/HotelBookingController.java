package com.example.backendweb.Controller.Booking;

import com.example.backendweb.Entity.Booking.HotelBooking;
import com.example.backendweb.Services.HotelBookingService;
import com.example.backendweb.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://172.31.26.105:9091/")
@RestController
@RequestMapping("/admins/hotelBookings")
public class HotelBookingController {

    @Autowired
    private HotelBookingService hotelBookingService;

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

    @GetMapping
    public ResponseEntity<?> getAllHotelBookings(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(hotelBookingService.getAllHotelBookings());
    }

    @PutMapping("/{hotelBookingId}")
    public ResponseEntity<?> updateHotelBooking(
            @PathVariable Integer hotelBookingId,
            @RequestBody HotelBooking hotelBooking,
            HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        HotelBooking updatedBooking = hotelBookingService.updateHotelBooking(hotelBookingId, hotelBooking);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{hotelBookingId}")
    public ResponseEntity<?> deleteHotelBooking(@PathVariable Integer hotelBookingId, HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        hotelBookingService.deleteHotelBooking(hotelBookingId);
        return ResponseEntity.noContent().build();
    }
}
