package com.example.backendweb.Controller;

import com.example.backendweb.Entity.Review.Review;
import com.example.backendweb.Services.JwtService;
import com.example.backendweb.Services.Review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

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
    public ResponseEntity<?> getAllReviews(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Integer reviewId, @RequestBody Review review, HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Review updatedReview = reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer reviewId, HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/reply")
    public ResponseEntity<?> replyToReview(
            @PathVariable Integer reviewId,
            @RequestBody Map<String, String> requestBody,
            HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String reply = requestBody.get("reply");
        Review updatedReview = reviewService.addReplyToReview(reviewId, reply);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping("/satisfaction")
    public ResponseEntity<?> getReviewSatisfaction(HttpServletRequest request) {
        if (!isAuthorized(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(reviewService.getReviewSatisfaction());
    }
}
