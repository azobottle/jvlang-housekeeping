package com.jvlang.housekeeping.services;

import com.jvlang.housekeeping.pojo.entity.Order0;
import com.jvlang.housekeeping.pojo.entity.Schedule;
import com.jvlang.housekeeping.pojo.exceptions.ScheduleFailed;
import com.jvlang.housekeeping.repo.OrderRepository;
import com.jvlang.housekeeping.repo.ScheduleRepository;
import com.jvlang.housekeeping.statemachine.OrderStatus;
import com.jvlang.housekeeping.statemachine.status.WaitPayment;
import com.jvlang.housekeeping.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Transactional
    public void createOrderByCustomer(Order0 order0) {
        long time = order0.getStartTime().getTime();
        LocalDateTime localDateTime = TimeUtils.getDateTimeOfTimestamp(time);
        int hour = localDateTime.getHour();


        Schedule e = new Schedule();
        e.setDate(Date.valueOf(localDateTime.toLocalDate()));
        e.setShifuId(order0.getShifuId());
        if (hour < 12) {
            e.setAvailableMor(true);
        } else {
            e.setAvailableAft(true);
        }

        Schedule schedule = scheduleRepository.findOne(Example.of(e)).orElse(null);
        if (schedule == null) {
            throw new ScheduleFailed("师傅在指定时间不上班");
        }

        if (hour < 12 && schedule.getAvailableMor()) {
            schedule.setAvailableMor(false);
        } else if (hour >= 12 && schedule.getAvailableAft()) {
            schedule.setAvailableAft(false);
        } else {
            throw new ScheduleFailed("师傅在指定时间不上班");
        }
        scheduleRepository.save(schedule);
        order0.setOrderStatusId(OrderStatus.getId(WaitPayment.class));
        orderRepository.save(order0);
    }
}
