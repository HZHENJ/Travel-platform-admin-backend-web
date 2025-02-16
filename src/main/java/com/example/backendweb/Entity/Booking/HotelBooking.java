package com.example.backendweb.Entity.Booking;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * @ClassName HotelBooking
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "hotel_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelBookingId; // 酒店预订唯一标识 (Primary Key)

    @Column(nullable = true)
    private Integer bookingId; // 预订ID (外键)

    @Column(nullable = false)
    private Integer hotelId; // 酒店ID (外键)

    @Column(nullable = false)
    private LocalDate checkInDate; // 入住日期

    @Column(nullable = false)
    private LocalDate checkOutDate; // 退房日期

    @Column(nullable = false, length = 100)
    private String roomType; // 房间类型

    @Column(nullable = false, length = 255)
    private String location; // 酒店位置

    @Column(nullable = false)
    private Integer guests; // 客人数
}
