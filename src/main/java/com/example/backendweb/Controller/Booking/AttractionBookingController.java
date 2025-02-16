package com.example.backendweb.Controller.Booking;

import com.example.backendweb.Entity.Booking.AttractionBooking;
import com.example.backendweb.Services.AttractionBookingService;
import com.example.backendweb.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://172.31.26.105:9091/")
@RestController
@RequestMapping("/admins/attractionBookings")
public class AttractionBookingController {

    @Autowired
    private AttractionBookingService attractionBookingService;

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
    public ResponseEntity<?> getAllAttractionBookings(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(attractionBookingService.getAllAttractionBookings());
    }

    @PutMapping("/{attractionBookingId}")
    public ResponseEntity<?> updateAttractionBooking(
            @PathVariable Integer attractionBookingId,
            @RequestBody AttractionBooking attractionBooking,
            HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        AttractionBooking updatedBooking = attractionBookingService.updateAttractionBooking(attractionBookingId, attractionBooking);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{attractionBookingId}")
    public ResponseEntity<?> deleteAttractionBooking(@PathVariable Integer attractionBookingId, HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        attractionBookingService.deleteAttractionBooking(attractionBookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/popular-attractions")
    public ResponseEntity<?> getPopularAttractions(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Map<String, Object>> popularAttractions = attractionBookingService.getAttractionsOrderedByBookingCount();
        return ResponseEntity.ok(popularAttractions);
    }
}
