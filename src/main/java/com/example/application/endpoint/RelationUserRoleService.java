package com.example.application.endpoint;

import com.example.application.endpoint.dto.RelationUserRoleDTO;
import com.example.application.persistence.document.RelationUserRole;
import com.example.application.persistence.document.Role;
import com.example.application.persistence.document.User;
import com.example.application.persistence.repository.RelationUserRoleRepo;
import com.example.application.persistence.repository.RoleRepo;
import com.example.application.persistence.repository.UserRepo;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudService;
import dev.hilla.crud.filter.Filter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


@BrowserCallable
@AnonymousAllowed
public class RelationUserRoleService implements CrudService<RelationUserRoleDTO, ObjectId> {
    @Autowired
    private RelationUserRoleRepo relationUserRoleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public @Nullable RelationUserRoleDTO save(RelationUserRoleDTO value) {//只允许删改关系
        RelationUserRole relationUserRole = new RelationUserRole();

        User user = new User();
        user.setName(value.getUserName());
        relationUserRole.setUserId(userRepo.findOne(Example.of(user)).orElse(null).getId());

        Role role = new Role();
        role.setName(value.getRoleName());
        relationUserRole.setRoleId(roleRepo.findOne(Example.of(role)).orElse(null).getId());

        relationUserRoleRepo.save(relationUserRole);
        return value;
    }

    @Override
    public void delete(ObjectId objectId) {
        relationUserRoleRepo.deleteById(objectId);
    }

    //1展示用户名、电话号码、角色名。分页
    //2根据角色名等值筛选，用户名、电话号码包含进行“与”筛选
    @Override
    public @Nonnull List<@Nonnull RelationUserRoleDTO> list(Pageable pageable, @Nullable Filter filter) {
        Page<RelationUserRole> relationUserRoles = relationUserRoleRepo.findAll(pageable);
        return relationUserRoles.stream().map(rela -> {
            RelationUserRoleDTO dto = new RelationUserRoleDTO();
            dto.setRelationId(rela.getId());
            dto.setUserId(rela.getUserId());
            dto.setRoleId(rela.getRoleId());

            User user = userRepo.findById(rela.getUserId()).orElse(null);
            Role role = roleRepo.findById(rela.getRoleId()).orElse(null);
            dto.setUserName(user.getName());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setRoleName(role.getName());
            return dto;
        }).toList();
    }
}
