package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.*;
import com.jvlang.housekeeping.repo.UserRoleRepository;
import com.jvlang.housekeeping.repo.UserRepository;
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
public class UserRoleEndpoint extends CrudRepositoryService<UserRole, Long, UserRoleRepository> {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserRole> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
    }

    @Override
    public Optional<UserRole> get(Long aLong) {
        return super.get(aLong).map(this::toVo);
    }

    protected UserRole toVo(UserRole dao) {
        return dao.toBuilder()
                .user(userRepository.findById(dao.getUserId()).orElse(null))
                .build();
    }
}
