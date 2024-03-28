package com.jvlang.housekeeping.pojo.vo;

import com.jvlang.housekeeping.pojo.Picture;
import com.jvlang.housekeeping.pojo.entity.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

/**
 * 用户公开的信息。
 * <p/>
 * 请注意，这些信息一般会对外公开，请勿在其中写入敏感信息。
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserPubInfo {
    Long id;

    @Nullable
    String displayName;

    @Nullable
    String description;

    @Nullable
    Picture avatarUrl;

    public static UserPubInfo from(@NonNull User user) {
        var id = user.getId();
        if (id == null) {
            throw new NullPointerException("Why id is null ? User is " + user);
        }
        return builder()
                .id(id)
                .description(user.getDescription())
                .displayName(Optional
                        .ofNullable(user.getNickName())
                        .map(it -> it.isBlank() ? null : it)
                        .or(() -> Optional
                                .ofNullable(user.getUsername())
                                .map(it -> it.isBlank() ? null : it)
                        )
                        .orElse(null)
                )
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
