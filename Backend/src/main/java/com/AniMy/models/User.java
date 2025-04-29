package com.AniMy.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(
        unique = true,
        nullable = false
    )
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(
        unique = true,
        nullable = false
    )
    @JsonIgnore
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role = UserRoles.ROLE_USER;


    @Column(nullable = false)
    private String avatar = "https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/default-avatar.jpg";

    @Column
    private String bio;

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
        this.role = UserRoles.ROLE_USER;
        this.enabled = false;
        this.avatar = "https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/default-avatar.jpg";
    }
}
