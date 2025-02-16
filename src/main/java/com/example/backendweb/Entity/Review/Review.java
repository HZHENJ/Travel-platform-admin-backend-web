package com.example.backendweb.Entity.Review;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Review
 * @Description Review entity with additional reply functionality
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId; // Primary Key

    @Column(nullable = false)
    private Integer userId; // User ID (Foreign Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType; // Item Type (Flight, Hotel, or Attraction)

    @Column(nullable = false)
    private Integer itemId; // Item ID (Flight ID, Hotel ID, or Attraction ID)

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating; // Rating

    @Column(columnDefinition = "TEXT")
    private String comment; // Review Comment

    @Column(columnDefinition = "TEXT")
    private String reply; // Admin Reply

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status; // Review Status (show or hide)

    @Column(nullable = false, updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // Created Time

    @Column(nullable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // Updated Time

    public enum ItemType {
        Flight, Hotel, Attraction
    }

    public enum ReviewStatus {
        hide, show
    }

    // Custom constructor with required fields
    public Review(Integer userId, ItemType itemType, Integer itemId,
                  BigDecimal rating, String comment) {
        this.userId = userId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.rating = rating;
        this.comment = comment;
        this.status = ReviewStatus.show; // Default status
    }

    // Helper method to add or update reply
    public void addReply(String reply) {
        this.reply = reply;
    }

    // Helper method to toggle status
    public void toggleStatus() {
        this.status = (this.status == ReviewStatus.show) ?
                ReviewStatus.hide : ReviewStatus.show;
    }

    // Helper method to update review
    public void updateReview(BigDecimal rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    // Helper method to check if review has reply
    public boolean hasReply() {
        return reply != null && !reply.trim().isEmpty();
    }
}
