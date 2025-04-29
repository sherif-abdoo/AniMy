package com.AniMy.services;

import com.AniMy.Dto.ProfileDto;
import com.AniMy.Dto.WatchListDto;
import com.AniMy.models.*;
import com.AniMy.repository.UserFriendsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileFactory {
    private final GetListService getListService;
    private final UserServices userServices;
    private final UserFriendsRepository userFriendsRepository;
    public ProfileDto createProfile(String username , String username2) {
        User user = (User) userServices.loadUserByUsername(username);
        List<UserFriends> friends = userFriendsRepository.findByUserAndStatus(user , FriendsStatus.ACCEPTED);
        List<WatchList> favourites = getListService.getUserList(username, WatchListStatus.valueOf("FAVORITE"));
        List<WatchList> watched = getListService.getUserList(username, WatchListStatus.valueOf("WATCHED"));
        List<WatchList> watching = getListService.getUserList(username, WatchListStatus.valueOf("WATCHING"));
        Optional<FriendsStatus> status = Optional.empty();
        if(username2 != null) {
            User user2 = (User) userServices.loadUserByUsername(username2);
            status = userFriendsRepository.getStatusByUser1AndUser2(user,user2);
        }
        System.out.println("----------------------------"+status);
        return ProfileDto.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .bio(user.getBio())
                .friendsCount(friends.size())
                .status(status)
                .favourite(favourites.stream().map(anime ->
                        new WatchListDto(anime.getAnimeId(),anime.getStatus())).toList())
                .watched(watched.stream().map(anime ->
                        new WatchListDto(anime.getAnimeId(),anime.getStatus())).toList())
                .watching(watching.stream().map(anime ->
                        new WatchListDto(anime.getAnimeId(),anime.getStatus())).toList())
                .build();
    }
}
