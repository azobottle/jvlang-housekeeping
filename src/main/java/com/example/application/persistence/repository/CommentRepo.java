package com.example.application.persistence.repository;

import com.example.application.persistence.document.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepo extends MongoRepository<Comment, ObjectId> {
}
