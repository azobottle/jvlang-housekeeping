package com.jvlang.housekeeping.pojo.entity.repo;

import com.jvlang.housekeeping.pojo.entity.RelationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelationUserRoleRepository extends JpaRepository<RelationUserRole, Long>,
        JpaSpecificationExecutor<RelationUserRole> {
}
