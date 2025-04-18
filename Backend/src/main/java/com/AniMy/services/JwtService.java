package com.AniMy.services;

import com.AniMy.config.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(UserDetails user, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getAccess().getSecret().getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getAccess().getExpiration()))
                .withIssuer(issuer)
                .withClaim("role", getRoles(user))
                .sign(algorithm);
    }

    public String generateRefreshToken(UserDetails user, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getRefresh().getSecret().getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getRefresh().getExpiration()))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token,boolean access){
        Algorithm algorithm = Algorithm.HMAC256(access ?
                jwtConfig.getAccess().getSecret().getBytes()
                : jwtConfig.getRefresh().getSecret().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    public Collection<SimpleGrantedAuthority> extractAuthorities(DecodedJWT jwt) {
        String role[] = jwt.getClaim("role").asArray(String.class);
        if(role == null) return null;
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(role).forEach(roleName ->{
            authorities.add(new SimpleGrantedAuthority(roleName));
        });
        return authorities;
    }

    public UsernamePasswordAuthenticationToken buildAuthentication(DecodedJWT jwt) {
        return new UsernamePasswordAuthenticationToken(
                jwt.getSubject(),
                null,
                extractAuthorities(jwt)
        );
    }

    private List<String> getRoles(UserDetails user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
