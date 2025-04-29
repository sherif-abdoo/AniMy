package com.AniMy.controller.Public;


import com.AniMy.Dto.ProfileDto;
import com.AniMy.Dto.UserDto;
import com.AniMy.models.User;
import com.AniMy.services.ProfileFactory;
import com.AniMy.services.UserServices;
import com.AniMy.utils.JSendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/profile")
@RequiredArgsConstructor
public class Profile {
    private final UserServices userServices;
    private final ProfileFactory profileFactory;
    @GetMapping("/{username}")
    public ResponseEntity<JSendResponse<Map<String, ProfileDto>>> getProfile
            (@PathVariable String username) {
        User user = (User) userServices.loadUserByUsername(username);
        if(user == null){
            return ResponseEntity.status(404).build();
        }
        ProfileDto profileDto = profileFactory.createProfile(user.getUsername(),null);
        JSendResponse<Map<String,ProfileDto>> res = new JSendResponse<>();
        Map<String,ProfileDto> data = new HashMap<>();
        data.put("profile", profileDto);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity
                .status(200)
                .body(res);
    }

    @GetMapping("/searchUsers")
    public ResponseEntity<JSendResponse<Map<String, List<UserDto>>>> searchUsers(
            @RequestParam String q) {
        List<UserDto> users = userServices.searchUsersByUsername(q);

        Map<String, List<UserDto>> data = new HashMap<>();
        data.put("users", users);

        JSendResponse<Map<String, List<UserDto>>> response = new JSendResponse<>();
        response.setStatus("success");
        response.setData(data);
        response.setMessage(null);
        return ResponseEntity.status(200).body(response);
    }



}
