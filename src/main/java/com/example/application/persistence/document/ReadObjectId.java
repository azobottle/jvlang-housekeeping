package com.example.application.persistence.document;

import org.bson.types.ObjectId;

@FunctionalInterface
public interface ReadObjectId {
    ObjectId read_object_id();
}
