package com.AniMy.repository;

import com.AniMy.models.WatchList;
import com.AniMy.models.WatchListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    WatchList findByUserUsernameAndAnimeId(String username, String animeId);
    List<WatchList> findByUserUsernameAndStatus(String username,WatchListStatus status);
}