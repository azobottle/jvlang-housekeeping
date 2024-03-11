package com.example.application.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Objects;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;

@Slf4j
public final class Mongos {
    private Mongos() {
    }

    public static <T> MongoCollection<Document> getCollection(MongoTemplate mongo, Class<T> clz) {
        return mongo.getCollection(mongo.getCollectionName(clz));
    }

    public static ObjectId toOid(String id) {
        return new ObjectId(id);
    }

    @SneakyThrows
    public static <T> Optional<T> findByIdFilterInvalidId(MongoTemplate mongo,
                                                          Class<T> clz,
                                                          String id,
                                                          ObjectMapper objectMapper) {
        var comment_info = "id=" + id + " , clz=" + clz;
        if (!ObjectId.isValid(id)) {
            log.warn("Invalid object id format ! " + comment_info);
            return Optional.empty();
        }
        try (var c = getCollection(mongo, clz)
                .find()
                .filter(eq("_id", toOid(id)))
                .comment("[mongos] findById : " + comment_info)
                .cursor()) {
            if (c.hasNext()) {
                var result = c.next();
                log.debug("[mongos] findById : " + comment_info + " , result=" + result);
                String item = null;
                T res1 = null;
                try {
                    item = result.toJson();
                    res1 = objectMapper.readValue(item, clz);
                } finally {
                    log.debug("[mongos]     item result : {}\t\t\t\t===>\t\t\t\t{}",
                            item,
                            res1);
                }
                return Optional.of(res1);
            } else {
                log.debug("[mongos] findById not found : " + comment_info);
                return Optional.empty();
            }
        }
    }
}
