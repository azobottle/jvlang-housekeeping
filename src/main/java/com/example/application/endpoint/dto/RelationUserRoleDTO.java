package com.example.application.endpoint.dto;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class RelationUserRoleDTO {
    private ObjectId relationId;
    private ObjectId userId;
    private ObjectId roleId;
    private String userName;
    private String phoneNumber;
    private String roleName;
}
