package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.RelationShifuService;
import com.jvlang.housekeeping.repo.*;
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
public class RelationShifuServiceEndpoint extends CrudRepositoryService<RelationShifuService, Long, RelationShifuServiceRepository> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<RelationShifuService> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
    }

    @Override
    public Optional<RelationShifuService> get(Long aLong) {
        return super.get(aLong).map(this::toVo);
    }

    private RelationShifuService toVo(RelationShifuService dao) {
        return dao.toBuilder()
                .shifu(userRepository.findById(dao.getShifuId()).orElse(null))
                .service(serviceRepository.findById(dao.getServiceId()).orElse(null))
                .build();
    }
}
