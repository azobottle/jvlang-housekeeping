package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.Order0;
import com.jvlang.housekeeping.repo.OrderRepository;
import com.jvlang.housekeeping.repo.ServiceRepository;
import com.jvlang.housekeeping.repo.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class OrderEndpoint extends CrudRepositoryService<Order0, Long, OrderRepository> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Order0> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter).stream().map(this::toVo).toList();
    }

    @Override
    public Optional<Order0> get(Long aLong) {
        return super.get(aLong).map(this::toVo);
    }

    private Order0 toVo(Order0 dao) {
        return dao.toBuilder()
                .shifu(userRepository.findById(dao.getShifuId()).orElse(null))
                .customer(userRepository.findById(dao.getCustomerId()).orElse(null))
                .service(serviceRepository.findById(dao.getServiceId()).orElse(null))
//                .orderStatusDesc(OrderStatusEnum.getEnumById(dao.getOrderStatusId()).description)
//                .overEventDesc(OrderEventEnum.getEnumById(dao.getOverEventId()).description)
                .build();
    }
}
