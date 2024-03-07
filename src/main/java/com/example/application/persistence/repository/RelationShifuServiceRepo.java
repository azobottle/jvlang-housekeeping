package com.example.application.persistence.repository;

import com.example.application.persistence.document.RelationShifuService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelationShifuServiceRepo extends MongoRepository<RelationShifuService, ObjectId> {
}
