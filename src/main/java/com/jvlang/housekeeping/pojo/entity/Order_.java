package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Picture;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Order_ extends AbstractEntity {
    private Long customerId;
    private Long shifuId;
    private Long serviceId;
    private Integer orderStatusId;
    private Integer overEventId;
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Picture> pictures;
    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected User customer;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected User shifu;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected Service service;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected String overEventDesc;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected String orderStatusDesc;
}
