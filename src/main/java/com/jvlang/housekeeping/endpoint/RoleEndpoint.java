package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Role;
import com.jvlang.housekeeping.pojo.entity.repo.RoleRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class RoleEndpoint extends CrudRepositoryService<Role, Long, RoleRepository> {
    @Override
    public @Nullable Role save(Role value) {
        return super.save(value);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Override
    public List<Role> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter);
    }
}
