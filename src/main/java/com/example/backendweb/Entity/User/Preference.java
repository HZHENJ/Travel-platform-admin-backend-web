package com.example.backendweb.Entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Preference
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/24
 * @Version 1.0
 */


@Entity
@Table(name = "preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer preferenceId; // 偏好唯一标识 (Primary Key)

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user; // 外键关联用户表

    @Enumerated(EnumType.STRING)
    private TravelType travelType = TravelType.Single; // 旅行类型 (默认 Single)

    @Column(precision = 10, scale = 2)
    private BigDecimal budgetRange; // 预算范围

    @Column(nullable = false, length = 50)
    private String language = "en"; // 语言 (默认 en)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 更新时间

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum TravelType {
        Single, Couple, Family
    }

}
