package com.example.application.endpoint;
import com.example.application.persistence.document.Role;
import com.example.application.persistence.repository.RoleRepo;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudService;
import dev.hilla.crud.filter.Filter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class RoleService implements CrudService<Role, ObjectId> {
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public @Nullable Role save(Role value) {
        return roleRepo.save(value);
    }

    @Override
    public void delete(ObjectId objectId) {
        roleRepo.deleteById(objectId);
    }

    @Override
    public @Nonnull List<@Nonnull Role> list(Pageable pageable, @Nullable Filter filter) {
        Page<Role> all = roleRepo.findAll(pageable);
        return all.stream().toList();
    }
}
