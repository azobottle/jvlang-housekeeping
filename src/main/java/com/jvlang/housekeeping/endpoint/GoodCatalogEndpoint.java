package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowNoLogin;
import com.jvlang.housekeeping.aop.AllowRole;
import com.jvlang.housekeeping.aop.AllowRoleAll;
import com.jvlang.housekeeping.pojo.entity.GoodCatalog;
import com.jvlang.housekeeping.pojo.entity.RelationShifuService;
import com.jvlang.housekeeping.repo.GoodCatalogRepository;
import com.jvlang.housekeeping.repo.RelationShifuServiceRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jvlang.housekeeping.pojo.Role0.Manager;
import static com.jvlang.housekeeping.pojo.Role0.SuperAdmin;

@Endpoint
@AnonymousAllowed
@AllowRole({SuperAdmin, Manager})
public class GoodCatalogEndpoint extends AbstractCrudEndpoint<GoodCatalog,Long, GoodCatalogRepository> {
    @AllowNoLogin
    @Override
    public List<GoodCatalog> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter);
    }

    @AllowNoLogin
    @Override
    public Optional<GoodCatalog> get(Long aLong) {
        return super.get(aLong);
    }

    @AllowNoLogin
    @Override
    public boolean exists(Long aLong) {
        return super.exists(aLong);
    }

    @AllowNoLogin
    @Override
    public long count(@Nullable Filter filter) {
        return super.count(filter);
    }
}
