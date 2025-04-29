package com.AniMy.Dto;

import com.AniMy.models.FriendsStatus;
import com.AniMy.models.User;
import com.AniMy.models.WatchList;
import com.AniMy.repository.WatchListRepository;
import com.AniMy.services.GetListService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
public class ProfileDto {
    private final String username;
    private final String avatar;
    private final String bio;
    private final int friendsCount;
    private final Optional<FriendsStatus> status;
    private final List<WatchListDto>  favourite;
    private final List<WatchListDto>  watched;
    private final List<WatchListDto>  watching;
}
