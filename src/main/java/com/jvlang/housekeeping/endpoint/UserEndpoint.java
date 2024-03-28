package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowNoLogin;
import com.jvlang.housekeeping.aop.AllowRole;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.repo.UserRepository;
import com.jvlang.housekeeping.util.UserUtils;
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
@AllowRole({SuperAdmin, Manager}) // 默认的那些接口都得是管理者权限才能改和看，但是可以给一些特殊的VO接口。
public class UserEndpoint extends CrudRepositoryService<User, Long, UserRepository> {
    @Override
    public @Nullable User save(User value) {
        return super.save(value);
    }

    @Override
    public List<User> saveAll(Iterable<User> values) {
        return super.saveAll(values);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Override
    public void deleteAll(Iterable<Long> longs) {
        super.deleteAll(longs);
    }

    @Override
    public List<User> list(Pageable pageable, @Nullable Filter filter) {
        return super.list(pageable, filter);
    }

    @Override
    public Optional<User> get(Long aLong) {
        return super.get(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return super.exists(aLong);
    }

    @Override
    public long count(@Nullable Filter filter) {
        return super.count(filter);
    }

    @AllowNoLogin
    public Optional<UserPubInfo> getPubInfo(Long aLong) {
        return get(aLong).map(UserPubInfo::from);
    }

    @AllowNoLogin
    public Optional<User> me() {
        return Optional
                .ofNullable(UserUtils.getCurrentUser())
                .flatMap(it -> get(it.getUserId()));
    }
}
