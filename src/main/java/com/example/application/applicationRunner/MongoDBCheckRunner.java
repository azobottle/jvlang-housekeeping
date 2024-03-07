package com.example.application.applicationRunner;

import com.example.application.persistence.document.Comment;
import com.example.application.persistence.document.RelationShifuService;
import com.example.application.persistence.document.RelationUserRole;
import com.example.application.persistence.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
public class MongoDBCheckRunner implements ApplicationRunner {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mongoOperations.indexOps(User.class).ensureIndex(new Index().on("openid", Sort.Direction.ASC));
        mongoOperations.indexOps(User.class).ensureIndex(new Index().on("shifuSchedules.date", Sort.Direction.ASC));
        mongoOperations.indexOps(RelationUserRole.class).ensureIndex(new Index().on("userId", Sort.Direction.ASC));
        mongoOperations.indexOps(RelationUserRole.class).ensureIndex(new Index().on("roleId", Sort.Direction.ASC));
        mongoOperations.indexOps(RelationShifuService.class).ensureIndex(new Index().on("shifuId", Sort.Direction.ASC));
        mongoOperations.indexOps(RelationShifuService.class).ensureIndex(new Index().on("serviceId", Sort.Direction.ASC));
        mongoOperations.indexOps(Comment.class).ensureIndex(new Index().on("replyObjectId", Sort.Direction.ASC));
    }
}
