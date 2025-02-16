package com.example.backendweb.Entity.Booking;

import jakarta.persistence.*;
import lombok.*;

/**
 * @ClassName FlightBooking
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "flight_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightBookingId; // 航班预订唯一标识 (Primary Key)

    @Column(nullable = true)
    private Integer bookingId; // 预订ID (外键)

    @Column(nullable = false)
    private Integer flightId; // 航班ID (外键)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatClass seatClass; // 座位等级

    @Column(nullable = false, length = 255)
    private String passengerName; // 乘客姓名

    @Column(nullable = false, length = 50)
    private String passengerId; // 乘客证件号

    public enum SeatClass {
        Economy, Business, First
    }
}
