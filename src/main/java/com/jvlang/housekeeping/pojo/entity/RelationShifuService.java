package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(indexes = {@Index(columnList = "shifuId"), @Index(columnList = "serviceId")})
public class RelationShifuService extends AbstractEntity {
    private Long shifuId;
    private Long serviceId;
    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected User shifu;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected Service service;
}
