package com.AniMy.services;

import com.AniMy.models.User;
import com.AniMy.models.WatchList;
import com.AniMy.models.WatchListStatus;
import com.AniMy.repository.WatchListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddToListService {
    private UserServices userServices;
    private WatchListRepository watchListRepository;
    public String addToList(String animeId , String userName , WatchListStatus status) {
        User user = (User) userServices.loadUserByUsername(userName);

        WatchList exists = watchListRepository.findByUserUsernameAndAnimeId(userName, animeId);
        if(status == null){
            return "Invalid status";
        }
        if (exists != null) {
            if(exists.getStatus() == status) {
                return "The anime already added to "+ status;
            }
            else if(exists.getStatus() != status){
                exists.setStatus(status);
                watchListRepository.save(exists);
                return "The anime already added to "+ status;
            }
        }
        WatchList watchList = new WatchList();
        watchList.setAnimeId(animeId);
        watchList.setUser(user);
        watchList.setStatus(status);

        watchListRepository.save(watchList);


        return "Added to the list";
    }
}
