package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
}