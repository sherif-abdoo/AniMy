package com.AniMy.services;

import com.AniMy.config.JwtConfig;
import com.AniMy.models.User;
import com.AniMy.utils.ApiResponse;
import com.AniMy.utils.JSendResponse;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class LogInFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String issuer = request.getRequestURL().toString();
        String accessToken = jwtService.generateAccessToken(user, issuer);
        String refreshToken = jwtService.generateRefreshToken(user, issuer);

        // 🛠 Properly setting refresh token cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false); // true for HTTPS in production
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(refreshCookie);

        // ✅ Send access token in JSON response body
        ApiResponse<Map<String, String>> responseBody = new ApiResponse<>();
        responseBody.setMessage("Login successfully");

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        responseBody.setData(tokens);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        var errorResponse = new JSendResponse<>(
                "fail",
                Map.of("message", "Invalid username or password"),
                null
        );

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
