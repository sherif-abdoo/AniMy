package com.AniMy.controller;

import com.AniMy.services.RegistrationService;
import com.AniMy.utils.ApiResponse;
import com.AniMy.utils.registerRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/public/registration")
@AllArgsConstructor
public class Register {

    private RegistrationService registrationService;

    @GetMapping
    public String getRegistrationStatus() {
        return "hello";
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody registerRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public ResponseEntity<ApiResponse<String>> confirmRegistration(
            @RequestParam String token) {
        return registrationService.confirmToken(token);
    }

}
