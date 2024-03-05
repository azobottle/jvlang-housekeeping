package com.example.application.data.repository;

import com.example.application.data.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service,Long> {
}
