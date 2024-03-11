package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Role;
import com.jvlang.housekeeping.pojo.entity.RoleRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;

@Endpoint
@AnonymousAllowed
public class RoleEndpoint extends CrudRepositoryService<Role, Long, RoleRepository> {
}
