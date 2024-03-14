package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Role;
import com.jvlang.housekeeping.repo.RoleRepository;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;
import jakarta.annotation.security.RolesAllowed;

@Endpoint
@RolesAllowed("ADMIN")
public class RoleEndpoint extends CrudRepositoryService<Role, Long, RoleRepository> {
}
