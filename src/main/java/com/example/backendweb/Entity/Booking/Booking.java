package com.example.backendweb.Entity.Booking;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Booking
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId; // 预订唯一标识 (Primary Key)

    @Column(nullable = false)
    private Integer userId; // 用户ID (外键)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingType bookingType; // 预订类型 (航班、酒店或景点)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // 预订状态

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount; // 总金额

    // @Column(nullable = true)
    // private Integer paymentId; // 支付ID (外键)

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 更新时间

    public enum BookingType {
        Flight, Hotel, Attraction
    }

    public enum BookingStatus {
        Confirmed, Canceled, Pending
    }
}
