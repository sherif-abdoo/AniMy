package com.AniMy.controller.Public;

import com.AniMy.utils.JSendResponse;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<JSendResponse<Map<String, String>>> handleTokenExpired(TokenExpiredException ex) {
        log.warn("Token expired: {}", ex.getMessage());
        return buildFailResponse(HttpStatus.UNAUTHORIZED, "Token has expired");
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<JSendResponse<Map<String, String>>> handleJwtError(JWTVerificationException ex) {
        log.warn("JWT verification failed: {}", ex.getMessage());
        return buildFailResponse(HttpStatus.FORBIDDEN, "Invalid or malformed token");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSendResponse<Map<String, String>>> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception at {}: {}", req.getRequestURI(), ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }

    // ✅ New builders with message inside `data`
    private ResponseEntity<JSendResponse<Map<String, String>>> buildFailResponse(HttpStatus status, String msg) {
        return ResponseEntity
                .status(status)
                .body(new JSendResponse<>("fail", Map.of("message", msg), null));
    }

    private ResponseEntity<JSendResponse<Map<String, String>>> buildErrorResponse(HttpStatus status, String msg) {
        return ResponseEntity
                .status(status)
                .body(new JSendResponse<>("error", Map.of("message", msg), null));
    }
}
