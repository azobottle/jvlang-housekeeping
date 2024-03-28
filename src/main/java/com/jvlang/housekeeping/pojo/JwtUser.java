package com.jvlang.housekeeping.pojo;

import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.pojo.exceptions.AuthFailed;
import com.jvlang.housekeeping.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

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
