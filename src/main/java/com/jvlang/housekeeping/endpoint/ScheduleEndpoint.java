package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowRole;
import com.jvlang.housekeeping.pojo.entity.Schedule;
import com.jvlang.housekeeping.repo.ScheduleRepository;
import com.jvlang.housekeeping.repo.ServiceRepository;
import com.jvlang.housekeeping.repo.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jvlang.housekeeping.pojo.Role0.Manager;
import static com.jvlang.housekeeping.pojo.Role0.SuperAdmin;

@Endpoint
@AnonymousAllowed
@AllowRole({SuperAdmin, Manager})
public class ScheduleEndpoint extends CrudRepositoryService<Schedule, Long, ScheduleRepository> {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Schedule> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
    }

    public List<Schedule> selectAvailableSchedules_for_service_mouth(Long serviceId) {
        return scheduleRepository.selectAvailableSchedules_for_service_mouth(serviceId);
    }

    public List<Schedule> selectAvailableSchedules_for_service_mouth(Long serviceId, Long shifuId) {
        return scheduleRepository.selectAvailableSchedules_for_service_mouth(serviceId, shifuId);
    }

    @Override
    public Optional<Schedule> get(Long aLong) {
        return super.get(aLong).map(this::toVo);
    }

    private Schedule toVo(Schedule dao) {
        return dao.toBuilder()
                .shifu(userRepository.findById(dao.getShifuId()).orElse(null))
                .build();
    }
}
