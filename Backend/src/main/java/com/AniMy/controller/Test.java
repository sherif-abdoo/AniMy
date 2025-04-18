package com.AniMy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class Test {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "👑 Admin access granted";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userOnly() {
        return "🧑‍💻 User access granted";
    }

    @GetMapping("/any")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String bothCanAccess() {
        return "🚀 Any role (USER or ROLE_ADMIN) access granted";
    }

    @GetMapping("/public")
    public String publicAccess() {
        return "🌍 This is open to everyone";
    }
}
