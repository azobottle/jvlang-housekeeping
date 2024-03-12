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
public class RelationUserRole extends AbstractEntity {
    protected Long userId;
    protected Long roleId;

    @Data
    @SuperBuilder(toBuilder = true)
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class View1 extends RelationUserRole {
        protected String userName;
        protected String roleName;
        protected String phoneNumber;

        public View1(RelationUserRole parent) {
            super(parent.toBuilder());
        }
    }
}
