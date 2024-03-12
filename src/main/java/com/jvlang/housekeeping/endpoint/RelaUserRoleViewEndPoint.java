package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.RelationUserRole;
import com.jvlang.housekeeping.pojo.entity.Role;
import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.pojo.entity.repo.RelationUserRoleRepository;
import com.jvlang.housekeeping.pojo.entity.repo.RoleRepository;
import com.jvlang.housekeeping.pojo.entity.repo.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudService;
import dev.hilla.crud.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class RelaUserRoleViewEndPoint implements CrudService<RelationUserRole.View1, Long> {
    @Autowired
    private RelationUserRoleRepository relationUserRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RelationUserRole.View1 save(RelationUserRole.View1 value) {
        User user = userRepository.findByName(value.getUserName());
        Role role = roleRepository.findByName(value.getRoleName());
        RelationUserRole rela = new RelationUserRole();
        rela.setId(value.getId());
        rela.setUserId(user.getId());
        rela.setRoleId(role.getId());
        rela.setOptimisticLocking(value.getOptimisticLocking());
        RelationUserRole save = relationUserRoleRepository.save(rela);
        RelationUserRole.View1 build = new RelationUserRole.View1(save)
                .toBuilder()
                .userName(value.getUserName())
                .roleName(value.getRoleName())
                .phoneNumber(user.getPhoneNumber())
                .build();
        return build;
    }

    @Override
    public void delete(Long aLong) {
        relationUserRoleRepository.deleteById(aLong);
    }

    @Override
    public @Nonnull List<RelationUserRole.View1> list(Pageable pageable, @Nullable Filter filter) {
        Page<RelationUserRole> relationUserRoles = relationUserRoleRepository.findAll(pageable);
        List<RelationUserRole.View1> list = (List<RelationUserRole.View1>) relationUserRoles.stream().map(rela -> {
            User user = userRepository.findById(rela.getUserId()).orElse(null);
            return new RelationUserRole.View1(rela)
                    .toBuilder()
                    .userName(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .roleName(roleRepository.findById(rela.getRoleId()).orElse(null).getName())
                    .build();
        }).toList();
        return list;
    }
}
