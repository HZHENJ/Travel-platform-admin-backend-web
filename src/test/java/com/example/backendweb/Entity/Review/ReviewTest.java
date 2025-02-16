package com.example.backendweb.Entity.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.PersistenceException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ReviewTest {

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    private Review createSampleReview() {
        return Review.builder()
                .userId(1)
                .itemType(Review.ItemType.Hotel)
                .itemId(100)
                .rating(new BigDecimal("4.5"))
                .comment("Great hotel with excellent service")
                .status(Review.ReviewStatus.show)
                .build();
    }

    @Test
    public void testCreateReview() {
        Review review = createSampleReview();

        // Save and flush to DB
        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        // Retrieve the saved review
        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());

        // Verify all fields
        assertNotNull(retrievedReview.getReviewId());
        assertEquals(1, retrievedReview.getUserId());
        assertEquals(Review.ItemType.Hotel, retrievedReview.getItemType());
        assertEquals(100, retrievedReview.getItemId());
        assertEquals(0, new BigDecimal("4.5").compareTo(retrievedReview.getRating()));
        assertEquals("Great hotel with excellent service", retrievedReview.getComment());
        assertEquals(Review.ReviewStatus.show, retrievedReview.getStatus());
        assertNotNull(retrievedReview.getCreatedAt());
        assertNotNull(retrievedReview.getUpdatedAt());
    }

    @Test
    public void testCustomConstructor() {
        Review review = new Review(1, Review.ItemType.Flight, 200,
                new BigDecimal("4.0"), "Good flight experience");

        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());

        assertEquals(1, retrievedReview.getUserId());
        assertEquals(Review.ItemType.Flight, retrievedReview.getItemType());
        assertEquals(200, retrievedReview.getItemId());
        assertEquals(0, new BigDecimal("4.0").compareTo(retrievedReview.getRating()));
        assertEquals("Good flight experience", retrievedReview.getComment());
        assertEquals(Review.ReviewStatus.show, retrievedReview.getStatus());
    }

    @Test
    public void testAddReply() {
        Review review = createSampleReview();
        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
        retrievedReview.addReply("Thank you for your feedback!");
        entityManager.persistAndFlush(retrievedReview);
        entityManager.clear();

        Review updatedReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals("Thank you for your feedback!", updatedReview.getReply());
        assertTrue(updatedReview.hasReply());
    }

    @Test
    public void testToggleStatus() {
        Review review = createSampleReview();
        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals(Review.ReviewStatus.show, retrievedReview.getStatus());

        retrievedReview.toggleStatus();
        entityManager.persistAndFlush(retrievedReview);
        entityManager.clear();

        Review toggledReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals(Review.ReviewStatus.hide, toggledReview.getStatus());

        toggledReview.toggleStatus();
        entityManager.persistAndFlush(toggledReview);
        entityManager.clear();

        Review finalReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals(Review.ReviewStatus.show, finalReview.getStatus());
    }

    @Test
    public void testUpdateReview() {
        Review review = createSampleReview();
        Review savedReview = entityManager.persistAndFlush(review);
        LocalDateTime originalCreatedAt = savedReview.getCreatedAt();
        LocalDateTime originalUpdatedAt = savedReview.getUpdatedAt();
        entityManager.clear();

        // Short delay to ensure timestamp difference
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
        retrievedReview.updateReview(new BigDecimal("3.5"), "Updated comment");
        entityManager.persistAndFlush(retrievedReview);
        entityManager.clear();

        Review updatedReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals(0, new BigDecimal("3.5").compareTo(updatedReview.getRating()));
        assertEquals("Updated comment", updatedReview.getComment());
        assertNotEquals(originalUpdatedAt, updatedReview.getUpdatedAt());
    }

    @Test
    public void testHasReply() {
        Review review = createSampleReview();
        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertFalse(retrievedReview.hasReply());

        retrievedReview.addReply("   ");
        assertFalse(retrievedReview.hasReply());

        retrievedReview.addReply("Thank you!");
        assertTrue(retrievedReview.hasReply());
    }

    @Test
    public void testItemTypes() {
        for (Review.ItemType itemType : Review.ItemType.values()) {
            Review review = Review.builder()
                    .userId(1)
                    .itemType(itemType)
                    .itemId(100)
                    .rating(new BigDecimal("4.0"))
                    .comment("Test comment")
                    .status(Review.ReviewStatus.show)
                    .build();

            Review savedReview = entityManager.persistAndFlush(review);
            entityManager.clear();

            Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
            assertEquals(itemType, retrievedReview.getItemType());
        }
    }

    @Test
    public void testRatingPrecision() {
        Review review = Review.builder()
                .userId(1)
                .itemType(Review.ItemType.Hotel)
                .itemId(100)
                .rating(new BigDecimal("4.55")) // More than 1 decimal place
                .comment("Test comment")
                .status(Review.ReviewStatus.show)
                .build();

        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals(1, retrievedReview.getRating().scale());
        assertEquals(0, new BigDecimal("4.6").compareTo(retrievedReview.getRating()));
    }

    @Test
    public void testRequiredFields() {
        // Test missing userId
        assertThrows(PersistenceException.class, () -> {
            Review review = Review.builder()
                    .itemType(Review.ItemType.Hotel)
                    .itemId(100)
                    .rating(new BigDecimal("4.0"))
                    .status(Review.ReviewStatus.show)
                    .build();
            entityManager.persistAndFlush(review);
        });

        entityManager.clear();

        // Test missing itemType
        assertThrows(PersistenceException.class, () -> {
            Review review = Review.builder()
                    .userId(1)
                    .itemId(100)
                    .rating(new BigDecimal("4.0"))
                    .status(Review.ReviewStatus.show)
                    .build();
            entityManager.persistAndFlush(review);
        });
    }

    @Test
    public void testStatusEnum() {
        Review review = createSampleReview();
        review.setStatus(Review.ReviewStatus.hide);

        Review savedReview = entityManager.persistAndFlush(review);
        entityManager.clear();

        Review retrievedReview = entityManager.find(Review.class, savedReview.getReviewId());
        assertEquals(Review.ReviewStatus.hide, retrievedReview.getStatus());
    }
}