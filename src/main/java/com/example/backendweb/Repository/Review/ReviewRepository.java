package com.example.backendweb.Repository.Review;

import com.example.backendweb.Entity.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ReviewRepository
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/5
 * @Version 1.0
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUserId(Integer userId);
    List<Review> findByItemTypeAndItemId(Review.ItemType itemType, Integer itemId);

    @Query("SELECT new map(" +
            "COUNT(CASE WHEN r.rating >= 3.0 THEN 1 END) as satisfied, " +
            "COUNT(CASE WHEN r.rating < 3.0 THEN 1 END) as unsatisfied, " +
            "COUNT(r) as total) " +
            "FROM Review r ")
    Map<String, Long> getReviewSatisfaction();
}
