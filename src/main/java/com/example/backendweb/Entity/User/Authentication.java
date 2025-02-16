package com.example.backendweb.Entity.User;

import jakarta.persistence.*;
import lombok.*;

/**
 * @ClassName Authentication
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/24
 * @Version 1.0
 */

@Entity
@Table(name = "authentications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId; // 认证信息唯一标识 (Primary Key)

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user; // 外键关联用户表

    @Column(nullable = false, unique = true, length = 100)
    private String username; // 用户名 (唯一)

    @Column(nullable = false, length = 255)
    private String passwordHash; // 加密后的密码

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.User; // 用户角色 (默认 User)

    public enum Role {
        User, Admin
    }
}
