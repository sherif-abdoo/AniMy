package com.AniMy.services;

import com.AniMy.Dto.UserDto;
import com.AniMy.models.FriendsStatus;
import com.AniMy.models.User;
import com.AniMy.models.UserFriends;
import com.AniMy.repository.UserFriendsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserFriendsService {
    private final UserFriendsRepository userFriendsRepository;
    private final UserServices userServices;


    public String friendRequest(String fromUsername , String toUsername){
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);
        if(from.equals(to)){
            return "Can't friend yourself, duhh";
        }
        Optional<UserFriends> userFriends = userFriendsRepository.findByUser1AndUser2(from, to);
        if(userFriends.isPresent()){
            return "Request already sent";
        }
        UserFriends requst = new UserFriends();
        requst.setUser1(from);
        requst.setUser2(to);
        requst.setStatus(FriendsStatus.PENDING);
        userFriendsRepository.save(requst);
        return "Friend request sent";
    }

    public String acceptFriendRequest(String fromUsername , String toUsername){
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);
        UserFriends request = userFriendsRepository.findByUser1AndUser2(from, to)
                .orElseThrow(() -> new RuntimeException("No request found"));
        request.setStatus(FriendsStatus.ACCEPTED);
        userFriendsRepository.save(request);
        return "Friend request accepted";
    }

    public String rejectFriendRequest(String fromUsername , String toUsername){
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);
        UserFriends request = userFriendsRepository.findByUser1AndUser2(from , to)
                .orElseThrow(() -> new RuntimeException("No request found"));
        userFriendsRepository.delete(request);
        return "Friend request rejected";
    }

    public String blockFriend(String fromUsername , String toUsername){
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);
        Optional<UserFriends> optionalFriendship = userFriendsRepository.findFriendship(from , to);

        if (optionalFriendship.isEmpty()) {
            UserFriends block = new UserFriends();
            block.setUser1(from);
            block.setUser2(to);
            block.setStatus(FriendsStatus.BLOCKED);
            userFriendsRepository.save(block);
            return "User has been blocked successfully.";
        }

        UserFriends friendship = optionalFriendship.get();

        if (friendship.getStatus() == FriendsStatus.BLOCKED) {
            return "You already blocked this user.";
        }

        friendship.setStatus(FriendsStatus.BLOCKED);
        userFriendsRepository.save(friendship);
        return "Friend is blocked successfully.";
    }

    public String unblockFriend(String fromUsername , String toUsername){
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);
        UserFriends friendShip = userFriendsRepository.findByUser1AndUser2(from , to)
                .orElseThrow(() -> new RuntimeException("No request found"));
        if(!friendShip.getStatus().equals(FriendsStatus.BLOCKED)){
            return "You are not blocked";
        }
        userFriendsRepository.delete(friendShip);
        return "Friend is unblocked";
    }

    public List<UserDto> listFriends(String username) {
        User user = (User) userServices.loadUserByUsername(username);
        return userFriendsRepository.findByUserAndStatus(
                user, FriendsStatus.ACCEPTED
        ).stream().map(friend -> {
            UserDto userDto = new UserDto();
            if(friend.getUser1().getUsername().equals(username)){
                userDto.setUsername(friend.getUser2().getUsername());
                userDto.setAvatar(friend.getUser2().getAvatar());
            }
            else if(friend.getUser2().getUsername().equals(username)){
                userDto.setUsername(friend.getUser1().getUsername());
                userDto.setAvatar(friend.getUser1().getAvatar());
            }
            return userDto;
        }).collect(Collectors.toList());
    }

    public List<UserDto> getPendingRequests(String username) {
        User user = (User) userServices.loadUserByUsername(username);
        return userFriendsRepository.findByUser2AndStatus(user, FriendsStatus.PENDING)
                .stream()
                .map(friendship -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(friendship.getUser1().getUsername());
                    userDto.setAvatar(friendship.getUser1().getAvatar());
                    
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    public String cancelFriendRequest(String fromUsername, String toUsername) {
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);

        UserFriends request = userFriendsRepository.findByUser1AndUser2(from, to)
                .orElseThrow(() -> new RuntimeException("No request found"));

        if (!request.getStatus().equals(FriendsStatus.PENDING)) {
            return "No pending request to cancel";
        }

        userFriendsRepository.delete(request);
        return "Friend request canceled";
    }

    // Unfriend an accepted friend
    public String unfriend(String fromUsername, String toUsername) {
        User from = (User) userServices.loadUserByUsername(fromUsername);
        User to = (User) userServices.loadUserByUsername(toUsername);

        UserFriends friendship = userFriendsRepository.findFriendship(from, to)
                .orElseThrow(() -> new RuntimeException("No friendship found"));

        if (!friendship.getStatus().equals(FriendsStatus.ACCEPTED)) {
            return "You are not friends";
        }

        userFriendsRepository.delete(friendship);
        return "Friendship ended";
    }

}
