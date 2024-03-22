package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Picture;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(columnList = "topicId"))
public class Comment extends AbstractEntity {
    private String topicType;
    private Long topicId;
    private Long userId;
    private Long replyId;
    private Timestamp commentTime;
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Picture> pictures;
    private String status;
}
