package com.example.backendweb.Entity.Behavior;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * @ClassName SearchHistory
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "search_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer searchId; // 搜索记录唯一标识 (Primary Key)

    @Column(nullable = false)
    private Integer userId; // 用户ID (外键)

    @Column(nullable = false, length = 255)
    private String keywords; // 用户输入的搜索关键词

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SearchType searchType; // 搜索类型 (Hotel 或 Attraction)

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = true)
    private String filters; // 搜索条件 (JSON 格式)

    @Column(nullable = false)
    private Integer resultsCount; // 搜索结果数量

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 搜索时间

    public enum SearchType {
        Hotel, Attraction
    }
}
