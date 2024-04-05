package com.jvlang.housekeeping.pojo;

import lombok.*;
import lombok.experimental.SuperBuilder;

public interface JwtUser {
    long getUserId();

    @SuperBuilder
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    class Impl implements JwtUser {
        long userId;
    }
}
