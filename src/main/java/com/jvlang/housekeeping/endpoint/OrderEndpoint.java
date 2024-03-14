package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Order_;
import com.jvlang.housekeeping.repo.OrderRepository;
import com.jvlang.housekeeping.repo.ServiceRepository;
import com.jvlang.housekeeping.repo.UserRepository;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
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
public class OrderEndpoint extends CrudRepositoryService<Order_, Long, OrderRepository> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Order_> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
    }

    @Override
    public Optional<Order_> get(Long aLong) {
        return super.get(aLong).map(this::toVo);
    }

    private Order_ toVo(Order_ dao) {
        return dao.toBuilder()
                .shifu(userRepository.findById(dao.getShifuId()).orElse(null))
                .customer(userRepository.findById(dao.getCustomerId()).orElse(null))
                .service(serviceRepository.findById(dao.getServiceId()).orElse(null))
                .orderStatusDesc(OrderStatusEnum.getEnumById(dao.getOrderStatusId()).description)
                .overEventDesc(OrderEventEnum.getEnumById(dao.getOverEventId()).description)
                .build();
    }
}
