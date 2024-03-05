package com.example.application.persistence.repository;

import com.example.application.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
