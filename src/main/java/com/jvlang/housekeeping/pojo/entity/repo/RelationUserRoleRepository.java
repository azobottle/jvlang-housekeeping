package com.jvlang.housekeeping.pojo.entity.repo;

import com.jvlang.housekeeping.pojo.entity.RelationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelationUserRoleRepository extends JpaRepository<RelationUserRole, Long>,
        JpaSpecificationExecutor<RelationUserRole> {
    @Query("select r.name from User u,Role r,RelationUserRole relation where" +
            " relation.userId=u.id " +
            " and relation.roleId=r.id" +
            " and u.id=?1")
    List<String> findRoleNamesByUserId(Long userId);//数据应有ROLE_前缀。其实是@RolesAllowed("ADMIN")的用法，
}
