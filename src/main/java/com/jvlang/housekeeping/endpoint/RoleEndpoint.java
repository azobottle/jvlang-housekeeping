package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Role;
import com.jvlang.housekeeping.pojo.entity.RoleRepository;
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
}
