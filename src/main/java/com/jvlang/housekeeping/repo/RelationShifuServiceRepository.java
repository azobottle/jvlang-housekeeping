package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.RelationShifuService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelationShifuServiceRepository extends JpaRepository<RelationShifuService, Long>,
        JpaSpecificationExecutor<RelationShifuService> {
}
