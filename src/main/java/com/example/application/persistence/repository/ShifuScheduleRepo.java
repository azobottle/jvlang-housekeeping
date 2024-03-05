package com.example.application.persistence.repository;

import com.example.application.persistence.entity.ShifuSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShifuScheduleRepo extends JpaRepository<ShifuSchedule,Long> {
}
