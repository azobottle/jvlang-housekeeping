package com.example.application.endpoint;

import com.example.application.persistence.document.User;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.ListService;
import dev.hilla.crud.filter.AndFilter;
import dev.hilla.crud.filter.Filter;
import dev.hilla.crud.filter.OrFilter;
import dev.hilla.crud.filter.PropertyStringFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
@BrowserCallable
@AnonymousAllowed
public class UserService implements ListService<User> {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public @Nonnull List<@Nonnull User> list(Pageable pageable, @Nullable Filter filter) {
        return mongoTemplate.find(createQuery(pageable, filter), User.class);
    }

    private Query createQuery(Pageable pageable, Filter filter) {
        Query query = new Query();
        query.with(pageable);
        query.addCriteria(createCriteria(filter));
        return query;
    }

    private Criteria createCriteria(Filter filter) {
        Criteria criteria = new Criteria();
        if (filter instanceof AndFilter andFilter) {
            if (andFilter.getChildren().isEmpty()) {
                return criteria;
            } else {
                return criteria.andOperator(andFilter.getChildren().stream()
                        .map(this::createCriteria).toList());
            }
        } else if (filter instanceof OrFilter orFilter) {
            if (orFilter.getChildren().isEmpty()) {
                return criteria;
            } else {
                return criteria.orOperator(orFilter.getChildren().stream()
                        .map(this::createCriteria).toList());
            }
        } else if (filter instanceof PropertyStringFilter propertyFilter) {
            return switch (propertyFilter.getPropertyId()) {
                case "name", "openid", "phoneNumber" -> Criteria.where(propertyFilter.getPropertyId()).regex(propertyFilter.getFilterValue(), "i");
//                case "zipcode" ->
//                        switch (propertyFilter.getMatcher()) {
//                            case GREATER_THAN -> Criteria.where(propertyFilter.getPropertyId()).gt(Integer.valueOf(propertyFilter.getFilterValue()));
//                            case LESS_THAN -> Criteria.where(propertyFilter.getPropertyId()).lt(Integer.valueOf(propertyFilter.getFilterValue()));
//                            default -> Criteria.where(propertyFilter.getPropertyId()).is(Integer.valueOf(propertyFilter.getFilterValue()));
//                        };
                default -> throw new IllegalArgumentException("Unknown filter property " + propertyFilter.getPropertyId());
            };
        } else {
            throw new IllegalArgumentException("Unknown filter type " + filter.getClass().getName());
        }
    }
}
