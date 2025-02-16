package com.example.backendweb.Repository.Review;

import com.example.backendweb.Entity.Review.ReviewStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName ReviewStatsRepository
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/5
 * @Version 1.0
 */
public interface ReviewStatsRepository extends JpaRepository<ReviewStats, Integer> {
    List<ReviewStats> findByItemTypeOrderByAverageRatingDesc(ReviewStats.ItemType itemType);
}
