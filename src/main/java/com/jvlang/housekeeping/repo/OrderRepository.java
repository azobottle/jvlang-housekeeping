package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.Order_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order_, Long>,
        JpaSpecificationExecutor<Order_> {
}
