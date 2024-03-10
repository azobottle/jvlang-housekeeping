package com.example.application.endpoint;

import com.example.application.persistence.document.RelationUserRole;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.filter.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class  RelationUserRoleService extends CrudEndpoint.Rejecting<RelationUserRole> {
    @Override
    protected Class<RelationUserRole> _clz() {
        return RelationUserRole.class;
    }

    @Override
    public @Nullable RelationUserRole save(@Nonnull RelationUserRole value) {
        return super.save(value);
    }

    @Override
    public void delete(String s) {
        super.delete(s);
    }

    @Override
    public @Nonnull List<@Nonnull RelationUserRole> list(Pageable pageable,
                                                         @org.springframework.lang.Nullable
                                                         @Nullable Filter filter) {
        return super.list(pageable, filter);

    }
}
