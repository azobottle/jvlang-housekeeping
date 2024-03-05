package com.example.application.persistence.repository;

import com.example.application.persistence.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service,Long> {
}
