package com.example.application.persistence.document;

import com.example.application.persistence.pojo.Picture;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Comment extends AbstractDocument {
    private String commentType;

    private ObjectId replyObjectId;

    private User user;

    private ObjectId replyCommentId;

    private List<Picture> commentPicArray;

    private String commentState;
}
