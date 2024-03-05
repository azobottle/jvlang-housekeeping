package com.example.application.persistence.repository;

import com.example.application.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
