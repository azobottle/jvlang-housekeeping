package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class User extends AbstractEntity {
    String name;

    String openid;

    String phoneNumber;

    String password;

    String salt;

//    @JdbcTypeCode(SqlTypes.JSON)
//    Picture avatarUrl;
//
//    LocalDateTime birthday;

//    @JdbcTypeCode(SqlTypes.JSON)
//    List<String> otherNames;
}
