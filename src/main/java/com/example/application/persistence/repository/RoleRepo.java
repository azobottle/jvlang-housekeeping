package com.example.application.persistence.repository;

import com.example.application.persistence.document.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepo extends MongoRepository<Role, ObjectId> {
}
