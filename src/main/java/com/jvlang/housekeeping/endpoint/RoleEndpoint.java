package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowRole;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.entity.Role;
import com.jvlang.housekeeping.repo.RoleRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;

@Endpoint
@AnonymousAllowed
@AllowRole(Role0.SuperAdmin)
public class RoleEndpoint extends CrudRepositoryService<Role, Long, RoleRepository> {
}
