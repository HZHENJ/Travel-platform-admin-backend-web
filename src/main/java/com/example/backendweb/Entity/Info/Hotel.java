package com.example.backendweb.Entity.Info;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * @ClassName Hotel
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/24
 * @Version 1.0
 */
@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId; // 酒店唯一标识

    @Column(nullable = false, length = 255)
    private String hotelName; // 酒店名称

    @Column(nullable = false, length = 255)
    private String location; // 酒店位置

    @Column(columnDefinition = "TEXT")
    private String description; // 酒店描述

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Double> roomType; // 房间类型及价格

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, String> amenities; // 酒店设施（如泳池、WiFi 等）

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Integer> roomAvailability; // 各房型剩余数量
}
