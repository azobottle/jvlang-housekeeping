package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowRole;
import com.jvlang.housekeeping.pojo.entity.*;
import com.jvlang.housekeeping.repo.UserRoleRepository;
import com.jvlang.housekeeping.repo.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jvlang.housekeeping.pojo.Role0.Manager;
import static com.jvlang.housekeeping.pojo.Role0.SuperAdmin;

@Endpoint
@AnonymousAllowed
@AllowRole({SuperAdmin, Manager})
public class UserRoleEndpoint extends AbstractCrudEndpoint<UserRole, Long, UserRoleRepository> {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserRole> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter);
    }

    @Override
    public Optional<UserRole> get(Long aLong) {
        return super.get(aLong);
    }
}
