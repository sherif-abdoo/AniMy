package com.AniMy.controller.User;

import com.AniMy.Dto.ProfileDto;
import com.AniMy.Dto.UserDto;
import com.AniMy.models.User;
import com.AniMy.services.ProfileFactory;
import com.AniMy.services.UserFriendsService;
import com.AniMy.services.UserServices;
import com.AniMy.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/friend")
@AllArgsConstructor
public class Friends {
    private final UserFriendsService userFriendsService;
    private final UserServices userServices;
    private final ProfileFactory profileFactory;

    @GetMapping("/{username}")
    public ResponseEntity<JSendResponse<Map<String, ProfileDto>>> getProfile
            (@PathVariable String username,
             @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userServices.loadUserByUsername(username);
        if(user == null){
            return ResponseEntity.status(404).build();
        }
        ProfileDto profileDto = profileFactory.createProfile(user.getUsername(),userDetails.getUsername());
        JSendResponse<Map<String,ProfileDto>> res = new JSendResponse<>();
        Map<String,ProfileDto> data = new HashMap<>();
        data.put("profile", profileDto);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity
                .status(200)
                .body(res);
    }

    @GetMapping("/listFriends")
    public ResponseEntity<JSendResponse<Map<String,List<UserDto>>>>
        listFriends(@AuthenticationPrincipal UserDetails user) {
            List<UserDto> friends = userFriendsService.listFriends(user.getUsername());
            JSendResponse<Map<String,List<UserDto>>> res = new JSendResponse<>();
            Map<String,List<UserDto>> data = new HashMap<>();
            data.put("friends", friends);
            res.setData(data);
            res.setStatus("success");
            return ResponseEntity
                    .status(200)
                    .body(res);
    }

    @GetMapping("/listFriendRequests")
    public ResponseEntity<JSendResponse<Map<String,List<UserDto>>>>
        listFriendRequests(@AuthenticationPrincipal UserDetails user) {
            List<UserDto> friends = userFriendsService.getPendingRequests(user.getUsername());
            JSendResponse<Map<String,List<UserDto>>> res = new JSendResponse<>();
            Map<String,List<UserDto>> data = new HashMap<>();
            data.put("friends", friends);
            res.setData(data);
            res.setStatus("success");
            return ResponseEntity
                    .status(200)
                    .body(res);
    }

    @PostMapping("/request/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String,String>>>
        sendFriendRequest(@AuthenticationPrincipal UserDetails from,
                          @PathVariable String toUserName){
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        String response = userFriendsService.friendRequest(from.getUsername(), toUserName);
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/accept/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String,String>>>
        acceptFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable String toUserName){
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        String response = userFriendsService.acceptFriendRequest(
                 toUserName,userDetails.getUsername());
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/reject/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String,String>>>
        rejectFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable String toUserName){
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        String response = userFriendsService.rejectFriendRequest(
                 toUserName,userDetails.getUsername());
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity.status(200).body(res);
    }


    @PostMapping("/block/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String,String>>>
        blockFriend(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable String toUserName){
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        String response = userFriendsService.blockFriend(
                userDetails.getUsername(), toUserName);
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity.status(200).body(res);
    }
    @PostMapping("/unblock/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String,String>>>
        unBlockFriend(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable String toUserName){
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        String response = userFriendsService.unblockFriend(
                userDetails.getUsername(), toUserName);
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");
        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/cancel/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String, String>>> cancelFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String toUserName) {

        String response = userFriendsService.cancelFriendRequest(userDetails.getUsername(), toUserName);

        JSendResponse<Map<String, String>> res = new JSendResponse<>();
        Map<String, String> data = new HashMap<>();
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");

        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/unfriend/{toUserName}")
    public ResponseEntity<JSendResponse<Map<String, String>>> unfriend(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String toUserName) {

        String response = userFriendsService.unfriend(userDetails.getUsername(), toUserName);

        JSendResponse<Map<String, String>> res = new JSendResponse<>();
        Map<String, String> data = new HashMap<>();
        data.put("message", response);
        res.setData(data);
        res.setStatus("success");

        return ResponseEntity.status(200).body(res);
    }





}
