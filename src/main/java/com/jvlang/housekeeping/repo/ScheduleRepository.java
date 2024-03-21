package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>,
        JpaSpecificationExecutor<Schedule> {
    @Query(value = "select s.* from schedule s , relation_shifu_service r where " +
            "s.shifu_id=r.shifu_id and r.service_id=?1 and s.date>=DATE_ADD(now(),INTERVAL 30 DAY )  ",
            nativeQuery = true)
    List<Schedule> selectAvailableSchedules_for_service_mouth(Long serviceId);

    @Query(value = "select s.* from schedule s , relation_shifu_service r where " +
            "s.shifu_id=?2 and s.date>=DATE_ADD(now(),INTERVAL 30 DAY ) and s.shifu_id=r.shifu_id and r.service_id=?1 ",
            nativeQuery = true)
    List<Schedule> selectAvailableSchedules_for_service_mouth(Long serviceId, Long shifuId);
}
