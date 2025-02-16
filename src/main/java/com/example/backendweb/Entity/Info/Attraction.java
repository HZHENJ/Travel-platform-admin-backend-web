package com.example.backendweb.Entity.Info;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * @ClassName Attraction
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/24
 * @Version 1.0
 */

@Entity
@Table(name = "attractions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attractionId;

    @Column(nullable = false, unique = true)
    private String uuid; // 景点唯一标识

    @Column(nullable = false, length = 255)
    private String attractionName; // 景点名称

    @Column(nullable = false, length = 255)
    private String location; // 景点位置

    @Column(columnDefinition = "TEXT")
    private String description; // 景点描述

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, String> ticketType; // 门票类型及价格

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, String> openingHours; // 开放时间

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Integer> ticketAvailability; // 各类型票的剩余数量
}
