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

    @NonNull
    User readUserCached();

    @NonNull
    User readUserNoCache();

    void clearUserCache();

    @SuperBuilder
    @RequiredArgsConstructor
    @AllArgsConstructor
    @ToString
    class Impl implements JwtUser {
        @Getter
        @Setter
        long userId;

        @Transient
        private transient volatile User _cachedUser;

        /**
         * 通过 userId 读取用户。
         *
         * @return 返回值不会为空，如果为空会抛出异常。
         */
        @NonNull
        @Override
        public User readUserCached() {
            if (_cachedUser == null) synchronized (this) {
                if (_cachedUser == null) _cachedUser = readUserNoCache();
            }
            return _cachedUser;
        }

        @Override
        public void clearUserCache() {
            if (_cachedUser != null) synchronized (this) {
                if (_cachedUser != null) _cachedUser = null;
            }
        }

        @NonNull
        @Override
        public User readUserNoCache() {
            var userRepository = Application.getContext().getBean(UserRepository.class);
            try {
                return userRepository.getReferenceById(userId);
            } catch (EntityNotFoundException err) {
                throw new AuthFailed("用户不存在，没有找到userId为" + userId + "的用户！", err);
            }
        }
    }
}
