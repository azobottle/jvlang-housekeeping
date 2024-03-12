package com.jvlang.housekeeping.pojo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelationUserRoleRepository extends JpaRepository<RelationUserRole, Long>,
        JpaSpecificationExecutor<RelationUserRole> {
}
