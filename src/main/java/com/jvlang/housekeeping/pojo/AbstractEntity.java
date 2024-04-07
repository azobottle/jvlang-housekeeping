package com.jvlang.housekeeping.pojo;

import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import com.jvlang.housekeeping.util.UserUtils;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;


@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@MappedSuperclass
public abstract class AbstractEntity implements Computed {
    @Id
    @Nullable // 作为前端传入参数时这个会为null，所以标注nullable。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    @Nullable
    protected Long optimisticLocking;

    @CreationTimestamp
    @Nonnull
    protected LocalDateTime createTime;

    @UpdateTimestamp
    @Nonnull
    protected LocalDateTime modifyTime;

    @Nullable
    @UserId
    protected Long createUserId;

    @Nullable
    @UserId
    protected Long modifyUserId;

    @PrePersist
    protected void onCreate() {
        id = null;
        optimisticLocking = 0L;
        var uid = Optional.ofNullable(UserUtils.getCurrentUser())
                .map(JwtUser::getUserId)
                .orElse(null);
        createUserId = uid;
        modifyUserId = uid;
    }

    @PreUpdate
    protected void onUpdate() {
        modifyUserId = Optional.ofNullable(UserUtils.getCurrentUser())
                .map(JwtUser::getUserId)
                .orElse(null);
    }

    @Transient
    @Override
    public @Nonnull Map<@Nonnull Long, @Nonnull UserPubInfo> getUserPubInfos() {
        return Computed.super.getUserPubInfos();
    }
}
