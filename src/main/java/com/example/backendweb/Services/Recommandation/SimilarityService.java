package com.example.backendweb.Services.Recommandation;

import com.example.backendweb.Entity.Review.Review;
import com.example.backendweb.Repository.Review.ReviewRepository;
import com.example.backendweb.Util.CosineSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SimilarityService
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/5
 * @Version 1.0
 */

@Service
public class SimilarityService {

    @Autowired
    private ReviewRepository reviewRepository;


    // Calculate the similarity between two users (based on cosine similarity)
    public double calculateUserSimilarity(Integer userId1, Integer userId2) {

        // Get the rating data of two users
        List<Review> user1Reviews = reviewRepository.findByUserId(userId1);
        List<Review> user2Reviews = reviewRepository.findByUserId(userId2);

        // Only calculate the rating similarity of attractions (Attraction) / Hotel
        Map<Long, Double> user1Ratings = user1Reviews.stream()
                .filter(r -> r.getItemType() == Review.ItemType.Attraction)
                .collect(Collectors.toMap(
                        r -> r.getItemId().longValue(),
                        r -> r.getRating().doubleValue()
                ));

        Map<Long, Double> user2Ratings = user2Reviews.stream()
                .filter(r -> r.getItemType() == Review.ItemType.Attraction)
                .collect(Collectors.toMap(
                        r -> r.getItemId().longValue(),
                        r -> r.getRating().doubleValue()
                ));

        // Directly call the CosineSimilarity tool class
        return CosineSimilarity.calculate(user1Ratings, user2Ratings);
    }
}
