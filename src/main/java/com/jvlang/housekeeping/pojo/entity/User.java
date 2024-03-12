package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Picture;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class User extends AbstractEntity {
    @Nullable
    String nickname;

    @Nullable
    String wxOpenid;

    @Nullable
    String description;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    Picture avatarUrl;

    @Nullable
    LocalDate birthday;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    List<String> otherNames;
}
