package com.example.application.persistence.repository;

import com.example.application.persistence.document.RelationUserRole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelationUserRoleRepo extends MongoRepository<RelationUserRole, ObjectId> {
}
