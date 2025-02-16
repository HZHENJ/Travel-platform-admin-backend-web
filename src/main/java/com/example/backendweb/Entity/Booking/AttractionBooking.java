package com.example.backendweb.Entity.Booking;

import com.example.backendweb.Entity.Info.Attraction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * @ClassName AttractionBooking
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "attraction_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttractionBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attractionBookingId; // 景点预订唯一标识 (Primary Key)

    @ManyToOne  // Define the relationship
    @JoinColumn(name = "attractionId", referencedColumnName = "attractionId", insertable = false, updatable = false) // Specify the join column
    private Attraction attraction;

    @Column(nullable = true)
    private Integer bookingId; // 预订ID (外键)

    @Column(nullable = false)
    private Integer attractionId; // 景点ID (外键)

    @Column(nullable = false)
    private LocalDate visitDate; // 参观日期

    @Column(nullable = false, length = 50)
    private String ticketType; // 门票类型

    @Column(nullable = false)
    private Integer numberOfTickets; // 门票数量
}
