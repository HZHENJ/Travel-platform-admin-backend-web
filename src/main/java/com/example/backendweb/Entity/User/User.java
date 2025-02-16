package com.example.backendweb.Entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName User
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/24
 * @Version 1.0
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // 用户唯一标识 (Primary Key)

    @Column(length = 100)
    private String name; // 用户名

    @Column(nullable = false, unique = true, length = 255)
    private String email; // 用户邮箱

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth; // 用户出生日期（可选）

    @Enumerated(EnumType.STRING)
    private Gender gender; // 用户性别

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 更新时间

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'Active'")
    private Status status; // 用户状态（Active/Inactive）

    @Column(nullable = false, length = 100)
    private String country; // 用户所在国家

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 性别枚举类型
    public enum Gender {
        M, F, Other
    }

    // 状态枚举类型
    public enum Status {
        Active, Inactive
    }
}
