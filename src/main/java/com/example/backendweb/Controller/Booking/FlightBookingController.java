package com.example.backendweb.Controller.Booking;

import com.example.backendweb.Entity.Booking.FlightBooking;
import com.example.backendweb.Services.FlightBookingService;
import com.example.backendweb.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://172.31.26.105:9091/")
@RestController
@RequestMapping("/admins/flightBookings")
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

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
    public ResponseEntity<?> getAllFlightBookings(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(flightBookingService.getAllFlightBookings());
    }

    @DeleteMapping("/{flightBookingId}")
    public ResponseEntity<?> deleteFlightBooking(@PathVariable Integer flightBookingId, HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        flightBookingService.deleteFlightBooking(flightBookingId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{flightBookingId}")
    public ResponseEntity<?> updateFlightBooking(
            @PathVariable Integer flightBookingId,
            @RequestBody FlightBooking flightBooking,
            HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        FlightBooking updatedBooking = flightBookingService.updateFlightBooking(flightBookingId, flightBooking);
        return ResponseEntity.ok(updatedBooking);
    }
}
