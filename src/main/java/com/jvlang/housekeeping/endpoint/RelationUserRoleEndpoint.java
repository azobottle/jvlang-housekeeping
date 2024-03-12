package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class RelationUserRoleEndpoint extends CrudRepositoryService<RelationUserRole, Long, RelationUserRoleRepository> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RelationUserRole> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
    }

    @Override
    public Optional<RelationUserRole> get(Long aLong) {
        return super.get(aLong).map(this::toVo);
    }

    private RelationUserRole toVo(RelationUserRole dao) {
        return dao.toBuilder()
                .user(userRepository.findById(dao.getUserId()).orElse(null))
                .role(roleRepository.findById(dao.getRoleId()).orElse(null))
                .build();
    }
}
