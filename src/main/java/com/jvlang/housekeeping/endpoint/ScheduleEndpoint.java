package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Schedule;
import com.jvlang.housekeeping.repo.ScheduleRepository;
import com.jvlang.housekeeping.repo.ServiceRepository;
import com.jvlang.housekeeping.repo.UserRepository;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Endpoint
@RolesAllowed("ADMIN")
public class ScheduleEndpoint extends CrudRepositoryService<Schedule, Long, ScheduleRepository> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Schedule> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
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
