package com.AniMy.utils;

import com.AniMy.utils.JSendResponse;

public class JSendUtils {
    public static <T> JSendResponse<T> success(T data) {
        return new JSendResponse<>("success", data, null);
    }

    public static <T> JSendResponse<T> fail(T data) {
        return new JSendResponse<>("fail", data, null);
    }

    public static JSendResponse<Object> error(String message) {
        return new JSendResponse<>("error", null, message);
    }
}
