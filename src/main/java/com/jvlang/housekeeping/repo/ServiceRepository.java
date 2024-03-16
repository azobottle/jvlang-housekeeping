package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<Service, Long>,
        JpaSpecificationExecutor<Service> {
}
