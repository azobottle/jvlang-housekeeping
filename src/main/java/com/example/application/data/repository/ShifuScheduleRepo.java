package com.example.application.data.repository;

import com.example.application.data.entity.Comment;
import com.example.application.data.entity.ShifuSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShifuScheduleRepo extends JpaRepository<ShifuSchedule,Long> {
}
