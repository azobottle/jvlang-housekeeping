package com.example.application.persistence.document;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Document
public class RelationUserRole extends AbstractDocument {
    protected String userId;
    protected String roleId;

    @Data
    @SuperBuilder(toBuilder = true)
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class View1 extends RelationUserRole {
        protected String userName;
        protected String roleName;

        public View1(RelationUserRole parent) {
            super(parent.toBuilder());
        }
    }
}
