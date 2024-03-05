package com.example.application.persistence.repository;

import com.example.application.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
