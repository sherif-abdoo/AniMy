package com.AniMy.Dto;

import com.AniMy.models.WatchListStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class WatchListDto {
    private String animeId;
    private WatchListStatus status;

}