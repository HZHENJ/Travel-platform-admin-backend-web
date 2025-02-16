package com.example.backendweb.Services.Review;

import com.example.backendweb.Entity.Review.Review;
import com.example.backendweb.Exception.ResourceNotFoundException;
import com.example.backendweb.Repository.Review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Integer reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setItemType(reviewDetails.getItemType());
        review.setItemId(reviewDetails.getItemId());
        // Don't update userId and reviewId as they are identity fields

        return reviewRepository.save(review);
    }

    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        reviewRepository.delete(review);
    }

    public Review addReplyToReview(Integer reviewId, String reply) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        review.setReply(reply);

        return reviewRepository.save(review);
    }

    public Map<String, Long> getReviewSatisfaction() {
        return reviewRepository.getReviewSatisfaction();
    }
}
