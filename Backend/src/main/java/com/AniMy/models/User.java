package com.AniMy.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    @Column(
        unique = true,
        nullable = false
    )
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(
        unique = true,
        nullable = false
    )
    private String email;

    @Column(nullable = false)
    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role = UserRoles.USER;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    //AllArgConstruct
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = UserRoles.USER;
        this.enabled = false;
    }
}
