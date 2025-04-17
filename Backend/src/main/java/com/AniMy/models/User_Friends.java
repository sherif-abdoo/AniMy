package com.AniMy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class User_Friends {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "friend1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "friend2_id", nullable = false)
    private User user2;

    @Enumerated(EnumType.STRING)
    private FriendsStatus status;

    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    private LocalDateTime updatedAt;
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
