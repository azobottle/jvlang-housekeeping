package com.jvlang.housekeeping.pojo.vo;

import com.jvlang.housekeeping.pojo.Picture;
import com.jvlang.housekeeping.pojo.entity.User;
import dev.hilla.Nonnull;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;

/**
 * 用户公开的信息。
 * <p/>
 * 请注意，这些信息一般会对外公开，请勿在其中写入敏感信息。
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserPubInfo {
    @Nonnull
    Long id;

    @Nullable
    String displayName;

    @Nullable
    String description;

    @Nullable
    Picture avatarUrl;

    @Nullable
    public static String displayNameFrom(@Nullable String nickName, @Nullable String username) {
        return Optional
                .ofNullable(nickName)
                .map(it -> it.isBlank() ? null : it)
                .or(() -> Optional
                        .ofNullable(username)
                        .map(it -> it.isBlank() ? null : it)
                )
                .orElse(null);
    }

    public static UserPubInfo from(@NonNull User user) {
        var id = user.getId();
        if (id == null) {
            throw new NullPointerException("Why id is null ? User is " + user);
        }
        return builder()
                .id(id)
                .description(user.getDescription())
                .displayName(displayNameFrom(user.getNickName(), user.getUsername()))
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
