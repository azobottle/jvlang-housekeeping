package com.example.application.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudService;
import dev.hilla.crud.filter.AndFilter;
import dev.hilla.crud.filter.Filter;
import dev.hilla.crud.filter.OrFilter;
import dev.hilla.crud.filter.PropertyStringFilter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.validation.BindException;
import org.springframework.validation.SmartValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Query.query;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

public abstract class CrudEndpoint<T> implements CrudService<T, String> {
    public static abstract class Rejecting<T> extends CrudEndpoint<T> {
        @Autowired
        protected MongoTemplate mongo;

        @Autowired
        protected ObjectMapper objectMapper;

        @Autowired
        protected SmartValidator smartValidator;

        @Override
        protected MongoTemplate _mongo() {
            return mongo;
        }

        @Override
        protected ObjectMapper _objectMapper() {
            return objectMapper;
        }

        @Override
        protected SmartValidator _config_smartValidator() {
            return smartValidator;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CrudEndpoint.class);

    protected abstract MongoTemplate _mongo();

    protected abstract ObjectMapper _objectMapper();

    protected abstract Class<T> _clz();

    protected abstract SmartValidator _config_smartValidator();

    protected void _validate(@Nonnull T value) {
        var errors = new BindException(value, value.getClass().getSimpleName());
        var v = _config_smartValidator();
        if (!v.supports(_clz())) {
            log.warn("validator not support class {} , validator is {}", _clz(), v);
        }
        v.validate(value, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getMessage());
        }
    }

    @Override
    public @Nullable T save(@Nonnull T value) {
        _validate(value);
        return _mongo().save(value);
    }

    @Override
    public void delete(@Nullable String s) {
        _mongo().remove(query(Criteria.where("_id").is(s)), _clz());
    }


    @SneakyThrows
    @Override

    public @Nonnull List<@Nonnull T> list(Pageable pageable,
                                          @Nullable
                                          @org.springframework.lang.Nullable
                                          Filter filter) {

        var mongo = _mongo();
        var result = new ArrayList<T>(pageable.isPaged() ? pageable.getPageSize() : 32);
        try (var c = Optional.of(
                        mongo
                                .getCollection(mongo.getCollectionName(_clz()))
                                .find()
                )
                // Condition options

                // TODO: 这个 Filter 暂时难搞，以后再说...

//                .map(o -> {
//                    if (filter == null) {
//                        return o;
//                    } else {
//                        return o.filter(filter2bson(filter, _config_FilterToBsonValueMapper()));
//                    }
//                })
                // Page options
                .map(o -> {
                    if (pageable.isUnpaged()) {
                        return o;
                    } else {
                        long offset = pageable.getOffset();
                        if (offset > Integer.MAX_VALUE) {
                            throw new IllegalArgumentException("pageable.offset large then " + Integer.MAX_VALUE
                                    + " , it's" + offset);
                        }
                        if (offset < 0) {
                            throw new IllegalArgumentException("pageable.offset less then 0 , it's " + offset);
                        }
                        int maxPageSize = _config_maxPageSize();
                        int pageSize = pageable.getPageSize();
                        if (pageSize > maxPageSize) {
                            throw new IllegalArgumentException("pageable.pageSize require less then " + maxPageSize
                                    + " , it's " + pageSize);
                        }
                        if (pageSize < 0) {
                            throw new IllegalArgumentException("pageable.pageSize less then 0 , it's " + offset);
                        }
                        return o.skip((int) offset).limit(pageSize);
                    }
                })
                // Sort options
                .map(o -> {
                    Sort sort = pageable.getSort();
                    if (sort.isUnsorted()) {
                        return o;
                    } else {
                        return o.sort(
                                orderBy(
                                        sort.stream().map(it -> {
                                            if (it.getDirection().isAscending()) {
                                                return ascending(it.getProperty());
                                            } else {
                                                return descending(it.getProperty());
                                            }
                                        }).toList()
                                )
                        );

                    }
                })
                .get()
                .comment("[crudEndpoint] list - this.class=" + this.getClass() + " , pageable="
                        + pageable + " , filter=" + filter)
                .cursor()) {
            while (c.hasNext()) {
                var obj = c.next();
                var oid = obj.getObjectId("_id");
                if (oid != null) {
                    obj.put("_id", oid.toHexString());
                }
                var item = obj.toJson();
                log.debug("[crudEndpoint] item result : {}", item);
                result.add(_objectMapper().readValue(item, _clz()));
            }
            return result;
        }
    }


    protected FilterToBsonValueMapper _config_FilterToBsonValueMapper() {
        return new FilterToBsonValueMapper() {
            @Override
            public String toString() {
                return "DEFAULT_for_" + CrudEndpoint.this;
            }

            public Object _default(PropertyStringFilter filter) {
                var filterValue = filter.getFilterValue();
                var propertyId = filter.getPropertyId();

                Object v = null;
                v = filterValue;

//                try {
//                    var schema = new JsonSchemaGenerator(_objectMapper()).generateSchema(_clz());
//
//                } catch (JsonMappingException e) {
//                    throw Lombok.sneakyThrow(e);
//                }
//
//
//                log.debug(
//                        "[{}.default] mapping propertyId={} : {} ===> {}  (type is {})",
//                        FilterToBsonValueMapper.class.getSimpleName(),
//                        propertyId, filterValue, v, v.getClass()
//                );

                return v;
            }

            @Override
            public Object eq(PropertyStringFilter filter) {
                return _default(filter);
            }

            @Override
            public Object gt(PropertyStringFilter filter) {
                return _default(filter);
            }

            @Override
            public Object lt(PropertyStringFilter filter) {
                return _default(filter);
            }

            @Override
            public Iterable<?> in(PropertyStringFilter filter) {
                if (filter instanceof Iterable<?> f) {
                    return f;
                }
                return List.of(_default(filter));
            }
        };
    }

    protected int _config_maxPageSize() {
        return 1024;
    }

    @Getter
    public static class FailedCastFilterToBsonException extends RuntimeException {
        protected final Filter filter;
        protected final FilterToBsonValueMapper m;

        public FailedCastFilterToBsonException(Filter filter, FilterToBsonValueMapper m, Throwable cause) {
            super("Failed cast filter to bson , filter is " + filter + " , value mapper is " + m + " .", cause);
            this.filter = filter;
            this.m = m;
        }
    }

    public interface FilterToBsonValueMapper {
        Object eq(PropertyStringFilter filter);

        Object gt(PropertyStringFilter filter);

        Object lt(PropertyStringFilter filter);

        Iterable<?> in(PropertyStringFilter filter);
    }

    private static Bson filter2bson(Filter filter, FilterToBsonValueMapper m) {
        try {
            if (filter instanceof AndFilter f) {
                var c = f.getChildren();
                if (c.isEmpty()) {
                    return empty();
                }
                return and(
                        c
                                .stream()
                                .map(it -> filter2bson(it, m))
                                .toList()
                );
            } else if (filter instanceof OrFilter f) {
                var c = f.getChildren();
                if (c.isEmpty()) {
                    return empty();
                }
                return or(
                        c
                                .stream()
                                .map(it -> filter2bson(it, m))
                                .toList()
                );
            } else if (filter instanceof PropertyStringFilter f) {
                var propertyId = f.getPropertyId();
                return switch (f.getMatcher()) {
                    case EQUALS -> eq(propertyId, m.eq(f));
                    case GREATER_THAN -> gt(propertyId, m.gt(f));
                    case LESS_THAN -> lt(propertyId, m.lt(f));
                    case CONTAINS -> in(propertyId, m.in(f));
                };
            } else {
                throw new IllegalArgumentException("Unrecognized filter type " + filter.getClass() +
                        " , filter is " + filter);
            }
        } catch (Throwable err) {
            throw new FailedCastFilterToBsonException(filter, m, err);
        }
    }
}
