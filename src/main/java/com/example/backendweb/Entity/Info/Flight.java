package com.example.backendweb.Entity.Info;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName Flight
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/24
 * @Version 1.0
 */

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId; // 航班唯一标识

    @Column(nullable = false, length = 50)
    private String flightNumber; // 航班号

    @Column(nullable = false, length = 255)
    private String airline; // 航空公司

    @Column(nullable = false, length = 100)
    private String departureCity; // 出发城市

    @Column(nullable = false, length = 100)
    private String arrivalCity; // 到达城市

    @Column(nullable = false, length = 255)
    private String departureAirport; // 出发机场

    @Column(nullable = false, length = 255)
    private String arrivalAirport; // 到达机场

    @Column(nullable = false)
    private LocalDateTime departureTime; // 出发时间

    @Column(nullable = false)
    private LocalDateTime arrivalTime; // 到达时间

    @Column(nullable = false, length = 50)
    private String duration; // 航程时长

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Integer> seatAvailability; // 各舱位剩余座位信息

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, String> seatType; // 座位类型

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus flightStatus; // 航班状态

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated; // 更新时间

    public enum FlightStatus {
        Scheduled, Delayed, Cancelled, Completed
    }
}
