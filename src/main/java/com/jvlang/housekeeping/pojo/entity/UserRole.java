package com.jvlang.housekeeping.pojo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Computed;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "userId"),
})
public class UserRole extends AbstractEntity {
    @UserId
    @ExcelProperty("用户id")
    @NotNull
    protected Long userId;

    @ExcelProperty("角色")
    @NotNull
    protected Role0 role;
}
