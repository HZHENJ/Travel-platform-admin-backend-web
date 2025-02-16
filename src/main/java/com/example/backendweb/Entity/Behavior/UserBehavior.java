package com.example.backendweb.Entity.Behavior;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @ClassName UserBehavior
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/1/25
 * @Version 1.0
 */

@Entity
@Table(name = "user_behaviors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBehavior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer behaviorId; // 行为记录唯一标识 (Primary Key)

    @Column(nullable = false)
    private Integer userId; // 用户ID (外键)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType; // 行为类型 (Click, View, Book, Search)

    @Column(nullable = false)
    private Integer actionTarget; // 行为目标ID (如推荐内容)

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime actionTime; // 行为发生时间

    @Column(nullable = true)
    private Integer duration; // 用户停留时间 (单位：秒)

    public enum ActionType {
        Click, View, Book, Search
    }
}
