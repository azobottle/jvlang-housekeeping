package com.jvlang.housekeeping.pojo.entity.repo;

import com.jvlang.housekeeping.pojo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long>,
        JpaSpecificationExecutor<Role> {
    @Query("select r from Role r where r.name=?1")
    Role findByName(String name);
}