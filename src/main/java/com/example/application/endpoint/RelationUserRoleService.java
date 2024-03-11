package com.example.application.endpoint;

import com.example.application.persistence.document.RelationUserRole;
import com.example.application.persistence.document.Role;
import com.example.application.persistence.document.User;
import com.example.application.util.Mongos;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.filter.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@BrowserCallable
@AnonymousAllowed
public class RelationUserRoleService extends CrudEndpoint.Rejecting<RelationUserRole, RelationUserRole.View1> {
    @Override
    protected Class<RelationUserRole> _clz_p() {
        return RelationUserRole.class;
    }

    @Override
    protected Class<RelationUserRole.View1> _clz_v() {
        return RelationUserRole.View1.class;
    }

    @Override
    protected RelationUserRole.View1 toView(RelationUserRole obj) {
        return new RelationUserRole.View1(obj).toBuilder()
                .roleName(Mongos
                        .findByIdFilterInvalidId(mongo, Role.class, obj.getRoleId(), objectMapper)
                        .map(Role::getName)
                        .orElse("【不存在此角色】"))
                .userName(Mongos
                        .findByIdFilterInvalidId(mongo, User.class, obj.getRoleId(), objectMapper)
                        .map(User::getName)
                        .orElse("【不存在此用户】"))
                .build();
    }

    @Override
    protected RelationUserRole toPersistence(RelationUserRole.View1 obj) {
        return obj;
    }

    @Override
    public RelationUserRole.@Nullable View1 save(RelationUserRole.@Nonnull View1 value) {
        return super.save(value);
    }

    @Override
    public void delete(String s) {
        super.delete(s);
    }

    @Override
    public @Nonnull List<RelationUserRole.@Nonnull View1> list(Pageable pageable,
                                                               @org.springframework.lang.Nullable
                                                               @Nullable Filter filter) {
        return super.list(pageable, filter);
    }
}
