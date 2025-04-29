package com.AniMy.controller.User;


import com.AniMy.models.User;
import com.AniMy.repository.UserRepository;
import com.AniMy.services.UserServices;
import com.AniMy.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.AniMy.utils.JSendUtils.success;

@RestController
@RequestMapping("/api/user/updateProfile")
@AllArgsConstructor
public class ProfileUpdates {

    private final UserServices userServices;
    private final UserRepository userRepository;

    @PatchMapping("/bio")
    public ResponseEntity<JSendResponse<Map<String, String>>> updateBio
            (@AuthenticationPrincipal UserDetails userDetails,
             @RequestBody Map<String,String> body) {
        String newBio = body.get("bio");
        User user = (User) userServices.loadUserByUsername(userDetails.getUsername());
        user.setBio(newBio);
        userRepository.save(user);

        return ResponseEntity
                .ok(success(Map.of("message", "Bio updated successfully")));
    }

    @PatchMapping("/avatar")
    public ResponseEntity<JSendResponse<Map<String, String>>> updateAvatar
            (@AuthenticationPrincipal UserDetails userDetails,
             @RequestBody Map<String,String> body) {
        String avatar = body.get("avatar");
        User user = (User) userServices.loadUserByUsername(userDetails.getUsername());
        user.setAvatar(avatar);
        userRepository.save(user);

        return ResponseEntity
                .ok(success(Map.of("message", "avatar updated successfully")));
    }


    @GetMapping ("/avatar")
    public ResponseEntity<JSendResponse<Map<String, String>>> getAvatar
            (@AuthenticationPrincipal UserDetails userDetails) {

        User user = (User) userServices.loadUserByUsername(userDetails.getUsername());
        String avatar = user.getAvatar();
        return ResponseEntity
                .ok(success(Map.of("avatar", avatar)));
    }
}
