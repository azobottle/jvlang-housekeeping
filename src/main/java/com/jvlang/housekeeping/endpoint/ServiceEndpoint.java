package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Service;
import com.jvlang.housekeeping.repo.ServiceRepository;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;
import jakarta.annotation.security.RolesAllowed;

@Endpoint
@RolesAllowed("ADMIN")
public class ServiceEndpoint extends CrudRepositoryService<Service, Long, ServiceRepository> {
}
