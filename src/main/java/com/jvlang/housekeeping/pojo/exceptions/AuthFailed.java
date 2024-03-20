package com.jvlang.housekeeping.pojo.exceptions;

import jakarta.annotation.Nullable;

public class AuthFailed extends RuntimeException implements BusinessFailed {
    public AuthFailed(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}