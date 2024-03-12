package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class RelationUserRole extends AbstractEntity {
    protected Long userId;
    protected Long roleId;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected User user;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected Role role;
}