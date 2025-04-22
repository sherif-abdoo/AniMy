package com.AniMy.services;

import com.AniMy.models.WatchList;
import com.AniMy.models.WatchListStatus;
import com.AniMy.repository.WatchListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GetListService {
    private final WatchListRepository watchListRepository;
    public List<WatchList> getUserList(String username, WatchListStatus status){
        return watchListRepository.findByUserUsernameAndStatus(username,status);
    }
}
