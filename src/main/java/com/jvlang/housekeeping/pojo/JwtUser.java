package com.jvlang.housekeeping.pojo;

import lombok.*;
import lombok.experimental.SuperBuilder;

public interface JwtUser {
    long getUserId();

    @SuperBuilder
    @RequiredArgsConstructor
    @AllArgsConstructor
    @ToString
    class Impl implements JwtUser {
        @Getter
        @Setter
        long userId;
    }
}
