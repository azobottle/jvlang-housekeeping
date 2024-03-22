package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Picture;
import com.jvlang.housekeeping.pojo.Role0;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "wxOpenid"),
        @Index(columnList = "username"),
})
public class User extends AbstractEntity {
    @Nullable
    String username;

    @Nullable
    String nickName;

    @Nullable
    String encodedPassword;

    @Nullable
    String phoneNumber;

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

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "userId")
    @Nullable
    List<UserRole> roles;
}
