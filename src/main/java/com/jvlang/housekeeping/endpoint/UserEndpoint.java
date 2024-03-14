package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.repo.UserRepository;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;
import jakarta.annotation.security.RolesAllowed;

@Endpoint
@RolesAllowed("ADMIN")
public class UserEndpoint extends CrudRepositoryService<User, Long, UserRepository> {
}
