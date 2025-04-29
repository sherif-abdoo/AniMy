package com.AniMy.controller.Public;

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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/refresh")
@RequiredArgsConstructor
public class RefreshToken {
    private final JwtService jwtService;
    private final UserServices userServices;
    @PostMapping
    public ResponseEntity<JSendResponse<Map<String, String>>>refreshToken(
            @CookieValue(value = "refreshToken", required = false)
                String refreshToken,
            HttpServletRequest request,
            HttpServletResponse response) {

        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        if(refreshToken != null) {
            DecodedJWT decodedJWT = jwtService.verifyToken(refreshToken,false);
            String username = decodedJWT.getSubject();
            String issuer = request.getRequestURL().toString();
            User user = (User)userServices.loadUserByUsername(username);
            String newAccessToken = jwtService.generateAccessToken(user,issuer);
            res.setStatus("success");
            data.put("accessToken",newAccessToken);
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
