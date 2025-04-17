package com.AniMy.repository;


import com.AniMy.models.EmailConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationToken, Long> {
    public EmailConfirmationToken findByToken(String token);
}
