package com.example.application.persistence.repository;

import com.example.application.persistence.document.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepo extends MongoRepository<Order, ObjectId> {
}
