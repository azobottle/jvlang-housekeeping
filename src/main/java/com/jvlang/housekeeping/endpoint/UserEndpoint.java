package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.pojo.entity.repo.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;

@Endpoint
@AnonymousAllowed
public class UserEndpoint extends CrudRepositoryService<User, Long, UserRepository> {
}
