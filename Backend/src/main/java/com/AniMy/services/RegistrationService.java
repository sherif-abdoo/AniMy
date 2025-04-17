package com.AniMy.services;

import com.AniMy.models.EmailConfirmationToken;
import com.AniMy.models.User;
import com.AniMy.utils.ApiResponse;
import com.AniMy.utils.RegistrationMsg;
import com.AniMy.utils.registerRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private UserServices userService;
    private PasswordEncoder passwordEncoder;
    private EmailConfirmationService emailConfirmationService;
    public ResponseEntity<ApiResponse<String>> register(registerRequest request) {
        boolean isValid = emailValidator.test(request.getEmail());
        if(!isValid){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            RegistrationMsg.EMAIL_NOT_VALID,
                            null));
        }
        String encodedPass = passwordEncoder.encode(request.getPassword());
        return userService.signUpUser(
                new User(
                        request.getUsername(),
                        encodedPass,
                        request.getEmail()
                )
        );
    }

    public ResponseEntity<ApiResponse<String>> confirmToken(String token){
        return emailConfirmationService.confirmToken(token);
    }
}
