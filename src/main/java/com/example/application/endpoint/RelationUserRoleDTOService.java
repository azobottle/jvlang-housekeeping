package com.example.application.endpoint;

import com.example.application.endpoint.dto.RelationUserRoleDTO;
import com.example.application.persistence.document.RelationUserRole;
import com.example.application.persistence.document.Role;
import com.example.application.persistence.document.User;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudService;
import dev.hilla.crud.filter.Filter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@BrowserCallable
@AnonymousAllowed
public class RelationUserRoleDTOService implements CrudService<RelationUserRoleDTO, String> {
    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private RelationUserRoleService relationUserRoleService;

    @Override
    public @Nullable RelationUserRoleDTO save(RelationUserRoleDTO value) {
        RelationUserRole relationUserRole = new RelationUserRole();
        User user = mongo.query(User.class)
                .matching(Query.query(where("name").is(value.getUserName())))
                .oneValue();
        relationUserRole.setUserId(user.getObjectId());

        ObjectId roleId = mongo.query(Role.class)
                .matching(Query.query(where("name").is(value.getRoleName())))
                .oneValue().getObjectId();
        relationUserRole.setRoleId(roleId);

        RelationUserRole save = relationUserRoleService.save(relationUserRole);
        value.setRelationId(save.getObjectId());
        value.setUserId(save.getUserId());
        value.setPhoneNumber(user.getPhoneNumber());
        value.setRoleId(save.getRoleId());
        return value;
    }

    @Override
    public void delete(String s) {
        relationUserRoleService.delete(s);
    }

    @Override
    public @Nonnull List<@Nonnull RelationUserRoleDTO> list(Pageable pageable, @Nullable Filter filter) {
        List<@Nonnull RelationUserRole> list = relationUserRoleService.list(pageable, filter);
        return list.stream().map(rela -> {
            RelationUserRoleDTO dto = new RelationUserRoleDTO();
            dto.setRelationId(rela.getObjectId());
            dto.setUserId(rela.getUserId());
            dto.setRoleId(rela.getRoleId());

            User user = mongo.findById(rela.getUserId(),User.class);
            Role role = mongo.findById(rela.getRoleId(), Role.class);
            dto.setUserName(user.getName());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setRoleName(role.getName());
            return dto;
        }).toList();
    }

}
