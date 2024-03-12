package com.jvlang.housekeeping.pojo.entity.repo;

import com.jvlang.housekeeping.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    @Query("select u from User u where u.nickName=?1")
    User findByNickName(String nickName);
}