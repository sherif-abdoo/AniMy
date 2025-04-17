package com.AniMy.services;


import com.AniMy.models.EmailConfirmationToken;
import com.AniMy.models.User;
import com.AniMy.repository.EmailConfirmationRepository;
import com.AniMy.repository.UserRepository;
import com.AniMy.utils.ApiResponse;
import com.AniMy.utils.RegistrationMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Getter
@Setter
@AllArgsConstructor
public class EmailConfirmationService {
    private final UserRepository userRepository;
    private final EmailConfirmationRepository emailConfirmationRepository;

    public EmailConfirmationToken createToken(User user){
        String token = UUID.randomUUID().toString();
        return new EmailConfirmationToken(token, LocalDateTime.now()
                , LocalDateTime.now().plusMinutes(5), user);
    }

    public void saveConfirmationToken(
            EmailConfirmationToken emailConfirmationToken) {
        emailConfirmationRepository.save(emailConfirmationToken);
    }

    public ResponseEntity<ApiResponse<String>> confirmToken(String token){
        EmailConfirmationToken emailConfirmationToken = emailConfirmationRepository.findByToken(token);
        System.out.println("confirming token from EmailConfirmationService: " + emailConfirmationToken);
        if(emailConfirmationToken == null || emailConfirmationToken.getConfirmedAt() != null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(RegistrationMsg.INVALID_TOKEN, null));
        }

        emailConfirmationToken.setConfirmedAt(LocalDateTime.now());
        if(emailConfirmationToken.getExpiresAt().isBefore(emailConfirmationToken.getConfirmedAt())){
            emailConfirmationRepository.delete(emailConfirmationToken);
            userRepository.delete(emailConfirmationToken.getUser());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(RegistrationMsg.EXPIRED_TOKEN, null));
        }

        User user = emailConfirmationToken.getUser();
        System.out.println("User" + user.getUsername());
        user.setEnabled(true);
        userRepository.save(user);
        emailConfirmationRepository.delete(emailConfirmationToken);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(RegistrationMsg.USER_REGISTRATION_SUCCESS, null));

    }
}
