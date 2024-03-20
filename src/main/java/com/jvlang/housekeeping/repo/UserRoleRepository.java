package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>,
        JpaSpecificationExecutor<UserRole> {
    @Query("select ur.role from User u inner join UserRole ur on ur.userId=u.id where u.id=?1")
    List<Role0> findRolesByUserId(Long userId);
}
