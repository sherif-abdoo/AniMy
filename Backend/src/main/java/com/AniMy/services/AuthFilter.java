package com.AniMy.services;

import com.AniMy.config.JwtConfig;
import com.AniMy.utils.ApiResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;

@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {


    private final JwtConfig jwtConfig;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().endsWith("login")) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorization = request.getHeader("Authorization");
            if(authorization != null && authorization.startsWith("Bearer ")) {
                try{
                    String token = authorization.substring("Bearer ".length());
                    DecodedJWT jwt = jwtService.verifyToken(token,true);
                    String username = jwt.getSubject();
                    UsernamePasswordAuthenticationToken authentication = jwtService.
                            buildAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);


                } catch (Exception e) {
                    ApiResponse<Map<String, String>> responseBody = new ApiResponse<>();
                    responseBody.setMessage("Forbidden");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    Map<String, String> error = new HashMap<>();
                    error.put("message", e.getMessage());
                    responseBody.setData(error);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
                }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
