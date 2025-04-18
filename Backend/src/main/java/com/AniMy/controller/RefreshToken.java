package com.AniMy.controller;

import com.AniMy.models.User;
import com.AniMy.services.JwtService;
import com.AniMy.services.UserServices;
import com.AniMy.utils.JSendResponse;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/refresh")
@RequiredArgsConstructor
public class RefreshToken {
    private final JwtService jwtService;
    private final UserServices userServices;
    @GetMapping
    public ResponseEntity<JSendResponse<Map<String, String>>>refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        if(authorization != null && authorization.startsWith("Bearer ")) {
            String refreshToken = authorization.substring(7);
            DecodedJWT decodedJWT = jwtService.verifyToken(refreshToken,false);
            String username = decodedJWT.getSubject();
            String issuer = request.getRequestURL().toString();
            User user = (User)userServices.loadUserByUsername(username);
            String newAccessToken = jwtService.generateAccessToken(user,issuer);
            res.setStatus("success");
            data.put("access_token",newAccessToken);
            res.setData(data);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(res);
        }
        else {
            res.setStatus("fail");
            data.put("message","invalid token");
            res.setData(data);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }
    }
}
