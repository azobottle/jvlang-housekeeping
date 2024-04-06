package com.jvlang.housekeeping;

import com.alibaba.excel.EasyExcel;
import com.jvlang.housekeeping.easyexcel.BaseExcelListener;
import com.jvlang.housekeeping.pojo.entity.Schedule;
import com.jvlang.housekeeping.repo.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.testng.Assert;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
public class TestEasyExcel {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    public void testScheduleExcel() {
//        String fileName = "src/test/resources/schedule.xlsx";
//        EasyExcel.read(fileName, Schedule.class, new BaseExcelListener<>(scheduleRepository)).sheet().doRead();
//        Schedule schedule = new Schedule();
//        schedule.setDate(Date.valueOf(LocalDate.of(2000, 3, 18)));
//        Assert.assertEquals(scheduleRepository.findOne(Example.of(schedule)).orElse(null).getShifuId(), 1);
    }
}
