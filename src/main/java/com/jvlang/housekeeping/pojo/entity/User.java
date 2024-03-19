package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Picture;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.repo.RelationUserRoleRepository;
import com.jvlang.housekeeping.repo.RoleRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
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
    // it should not null , but who knows
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

    @Transient
    List<Role0> roles;
}
