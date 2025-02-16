package com.example.backendweb.Entity.Recommendation;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName RecommendationContent
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "recommendation_contents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId; // 推荐内容唯一标识 (Primary Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType; // 内容类型 (Hotel 或 Attraction)

    @Column(nullable = false)
    private Integer referenceId; // 引用基础信息表的 ID (HotelInfo 或 AttractionInfo)

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score; // 推荐分数 (算法生成)

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 更新时间

    public enum ContentType {
        Hotel, Attraction
    }
}
