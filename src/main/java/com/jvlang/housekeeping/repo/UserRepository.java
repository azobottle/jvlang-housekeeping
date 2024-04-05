package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    default List<UserPubInfo> getPubInfos(List<Long> uids) {
        return this.findAllById(uids).stream().map(UserPubInfo::from).toList();
    }
}