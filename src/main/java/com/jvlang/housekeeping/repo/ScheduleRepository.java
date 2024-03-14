package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>,
        JpaSpecificationExecutor<Schedule> {
}
