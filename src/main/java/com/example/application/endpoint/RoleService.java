package com.example.application.endpoint;

import com.example.application.persistence.document.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.SmartValidator;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
@Slf4j
public class RoleService extends CrudEndpoint<Role> {
    @Autowired
    MongoTemplate mongo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SmartValidator smartValidator;

    @Override
    protected MongoTemplate _mongo() {
        return mongo;
    }

    @Override
    protected ObjectMapper _objectMapper() {
        return objectMapper;
    }

    @Override
    protected Class<Role> _clz() {
        return Role.class;
    }

    @Override
    protected SmartValidator _config_smartValidator() {
        return smartValidator;
    }

    @Override
    public @Nullable Role save(@Nonnull Role value) {
        return super.save(value);
    }

    @Override
    public void delete(String s) {
        super.delete(s);
    }

    @Override
    public @Nonnull List<@Nonnull Role> list(Pageable pageable,
                                             @org.springframework.lang.Nullable
                                             @Nullable Filter filter) {
        return super.list(pageable, filter);
    }
}
