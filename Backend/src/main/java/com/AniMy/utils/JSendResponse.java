package com.AniMy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSendResponse<T> {
    private String status; // "success", "fail", or "error"
    private T data;        // Data object (for success/fail)
    private String message; // For errors
}
