package com.example.backendweb.Entity.Review;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassName ReviewStats
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "review_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statId; // 统计唯一标识 (Primary Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType; // 评价对象类型 (酒店或景点)

    @Column(nullable = false)
    private Integer itemId; // 评价对象ID (酒店ID、景点ID)

    @Column(nullable = false)
    private Integer totalReviews; // 总评论数

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal averageRating; // 平均评分

    public enum ItemType {
        Hotel, Attraction
    }
}