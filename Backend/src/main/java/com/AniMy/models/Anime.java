package com.AniMy.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@EqualsAndHashCode
public class Anime {
    @Id
    @GeneratedValue
    private int id;

    @Column(
        unique = true,
        nullable = false
    )
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int episodesNumber;

    private float rating;

    @Column(nullable = false)
    private String season;
}
