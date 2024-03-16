package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Service;
import com.jvlang.housekeeping.repo.ServiceRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;
import jakarta.annotation.security.RolesAllowed;

@Endpoint
@AnonymousAllowed
//@RolesAllowed("ADMIN")
public class ServiceEndpoint extends CrudRepositoryService<Service, Long, ServiceRepository> {
}
