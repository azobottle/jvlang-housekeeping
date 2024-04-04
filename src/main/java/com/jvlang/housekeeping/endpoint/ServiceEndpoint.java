package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowRole;
import com.jvlang.housekeeping.pojo.entity.Service;
import com.jvlang.housekeeping.repo.ServiceRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.crud.CrudRepositoryService;
import jakarta.annotation.security.RolesAllowed;

import static com.jvlang.housekeeping.pojo.Role0.Manager;
import static com.jvlang.housekeeping.pojo.Role0.SuperAdmin;

@Endpoint
@AnonymousAllowed
@AllowRole({SuperAdmin, Manager})
public class ServiceEndpoint extends CrudRepositoryService<Service, Long, ServiceRepository> {
}
