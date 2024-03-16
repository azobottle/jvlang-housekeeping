package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.Order0;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order0, Long>,
        JpaSpecificationExecutor<Order0> {
}
