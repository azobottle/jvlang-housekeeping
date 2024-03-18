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
@Table(indexes = @Index(columnList = "userId"))
public class Address extends AbstractEntity {
    private Long userId;
    private String province;
    private String city;
    private String country;
    private String detail;
    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected Role role;
}
