package com.example.application.persistence.repository;

import com.example.application.persistence.document.Service;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepo extends MongoRepository<Service, ObjectId> {
}
