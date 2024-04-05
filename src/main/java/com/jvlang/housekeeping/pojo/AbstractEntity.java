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
    @Nullable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    @Nullable
    protected Long optimisticLocking;

    @Nullable
    @CreationTimestamp
    protected Timestamp createTime;

    @Nullable
    @UpdateTimestamp
    protected Timestamp modifyTime;

    @Nullable
    @UserId
    protected Long createUserId;

    @Nullable
    @UserId
    protected Long modifyUserId;

    @PrePersist
    protected void onCreate() {
        createUserId = Optional.ofNullable(UserUtils.getCurrentUser())
                .map(JwtUser::getUserId)
                .orElse(null);
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
