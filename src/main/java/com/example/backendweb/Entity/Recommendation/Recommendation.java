package com.example.backendweb.Entity.Recommendation;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Recommendation
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recommendationId; // 推荐记录唯一标识 (Primary Key)

    @Column(nullable = false)
    private Integer userId; // 用户ID (外键)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationType recommendationType; // 推荐类型 (User-Based 或 Item-Based)

    @Column(nullable = false)
    private Integer itemId; // 推荐项目ID

    @Column(nullable = false)
    private Integer contentId; // 推荐内容ID (外键，关联 RecommendationContent)

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score; // 推荐分数 (算法生成)

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime generatedAt; // 推荐生成时间

    public enum RecommendationType {
        UserBased, ItemBased
    }
}
