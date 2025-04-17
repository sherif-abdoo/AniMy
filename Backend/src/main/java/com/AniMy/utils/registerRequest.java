package com.AniMy.utils;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class registerRequest {
    private final String username;
    private final String password;
    private final String email;
}
