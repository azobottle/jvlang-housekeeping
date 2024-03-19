package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.Role0;
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
    List<Role0> findRoleNamesByUserId(Long userId);
}
