package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.pojo.entity.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class UserEndpoint extends CrudRepositoryService<User, Long, UserRepository> {
    @Override
    public @Nullable User save(User value) {
        return super.save(value);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Override
    public List<User> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter);
    }
}
