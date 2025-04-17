package com.AniMy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class EmailConfirmationToken {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id")
    private User user;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusMinutes(5);
    }

    @PreUpdate
    protected void onUpdate() {
        this.confirmedAt = LocalDateTime.now();
    }

    //constructor
    public EmailConfirmationToken(String token, LocalDateTime createdAt,
                                  LocalDateTime expiresAt,User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
