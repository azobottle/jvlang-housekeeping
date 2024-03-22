package com.jvlang.housekeeping.pojo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jvlang.housekeeping.easyexcel.converter.DateConverter;
import com.jvlang.housekeeping.pojo.AbstractEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "shifuId"}))
public class Schedule extends AbstractEntity {
    @ExcelProperty("师傅id")
    private Long shifuId;
    @ExcelProperty(value = "日期", converter = DateConverter.class)
    private Date date;
    @ExcelProperty("上午是否排班")
    private Boolean availableMor;
    @ExcelProperty("下午是否排班")
    private Boolean availableAft;
    @Transient
    @Setter(value = AccessLevel.NONE)
    @Nullable
    protected User shifu;
}
