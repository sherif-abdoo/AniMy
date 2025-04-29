package com.AniMy.Dto;

import com.AniMy.models.WatchListStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@Data
public class WatchListDto {
    @JsonProperty("animeId")
    private String animeId;
    private WatchListStatus status;

}