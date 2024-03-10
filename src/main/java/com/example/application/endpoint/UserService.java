package com.example.application.endpoint;

import com.example.application.persistence.document.User;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.filter.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

@BrowserCallable
@AnonymousAllowed

@Slf4j
public class UserService extends CrudEndpoint.Rejecting<User>{
    @Override
    protected Class<User> _clz() {
        return User.class;
    }

    @Override
    public @Nonnull List<@Nonnull User> list(Pageable pageable,
                                             @org.springframework.lang.Nullable
                                             @Nullable Filter filter) {
        return super.list(pageable, filter);

    }
}
