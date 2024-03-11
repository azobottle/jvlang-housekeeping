package com.example.application.endpoint;

import com.example.application.persistence.document.ReadObjectId;
import com.example.application.util.Jsons;
import com.example.application.util.Mongos;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudService;
import dev.hilla.crud.filter.AndFilter;
import dev.hilla.crud.filter.Filter;
import dev.hilla.crud.filter.OrFilter;
import dev.hilla.crud.filter.PropertyStringFilter;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
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
import java.util.Objects;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @param <TP> 存储层或参数层对象。警告：如果{@link TV}是{@link TP}的子类，则注意要在{@link #toPersistence(Object)}过滤掉视图层的脏数据！
 * @param <TV> 前端视图层对象，一般会带有一些“WritableComputed”属性。
 */
public abstract class CrudEndpoint<TP extends ReadObjectId, TV> implements CrudService<TV, String> {
    // hilla的生成器前端不会区分 view 和 param ， 因此只能给一个模型类。

    public static abstract class Rejecting<TP extends ReadObjectId, TV> extends CrudEndpoint<TP, TV> {
        public abstract static class Simple<T extends ReadObjectId> extends Rejecting<T, T> {
            @Override
            protected Class<T> _clz_v() {
                return _clz_p();
            }

            @Override
            protected T toView(T obj) {
                return obj;
            }

            @Override
            protected T toPersistence(T obj) {
                return obj;
            }
        }

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
        protected SmartValidator _smart_validator() {
            return smartValidator;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CrudEndpoint.class);

    protected abstract MongoTemplate _mongo();

    protected abstract ObjectMapper _objectMapper();

    protected abstract Class<TP> _clz_p();

    @SuppressWarnings("unused")
    protected abstract Class<TV> _clz_v();

    protected abstract TV toView(TP obj);

    /**
     * 警告：尽量不要用{@link TV}的父类作为{@link TP}！因为这很容易一不小心存入视图层的脏数据！
     */
    protected abstract TP toPersistence(TV obj);

    protected Config<TV, TP> _config() {
        return Config.<TV, TP>builder()
                .build();
    }

    @SuppressWarnings("unused")
    @Data
    @SuperBuilder(toBuilder = true)
    public static class Config<TV, TP> {
        @Builder.Default
        protected boolean warningSubclassOnSave = true;

        @Builder.Default
        protected int maxPageSize = 256;
    }

    protected abstract SmartValidator _smart_validator();

    protected void _validate(@Nonnull TP value) {
        var errors = new BindException(value, value.getClass().getSimpleName());
        var v = _smart_validator();
        if (!v.supports(_clz_p())) {
            log.warn("validator not support class {} , validator is {}", _clz_p(), v);
        }
        v.validate(value, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getMessage());
        }
    }

    @org.springframework.lang.Nullable
    protected @Nullable TP _save(@Nonnull TP value) {
        _validate(value);
        log.debug("[crudEndpoint] save value is {}", value);
        var mongo = _mongo();

        var _object_id = value.read_object_id();
        if (_object_id == null) {
            return mongo.save(value);
        } else {
            mongo.findAndReplace(
                    query(Criteria.where("_id").is(_object_id)),
                    value
            );
            return Mongos.findByIdFilterInvalidId(
                            mongo, _clz_p(), _object_id.toHexString(), _objectMapper())
                    .orElse(null);
        }
//            log.debug("[crudEndpoint]     upsert id is {}", _object_id);

//        return mongo.save(value);


//        if (value instanceof ReadObjectId _v) {
//            // 如果没传id说明是插入，传了id则是更新。
//            var id = _v.read_object_id();
//            log.debug("[crudEndpoint]     upsert id is {}", id);
//            var doc = new Document();
//            mongo.getConverter().write(value, doc);
//            var _query = query(Criteria.where("_id").is(id));
//            var _update = Update
//                    .fromDocument(doc, "_id", "id")
//                    .setOnInsert("id", ObjectId.get());
//            log.debug("[crudEndpoint]     upsert : Document is {} , Update is {}", doc, _update);
//            var res = mongo.upsert(
//                    _query,
//                    _update,
//                    mongo.getCollectionName(_clz_p())
//            );
//
//            var result_upsert_id = res.getUpsertedId();
//            log.debug("[crudEndpoint]     upsert result : upsertedId={} , matchedCount={} , modifiedCount={} , acknowledged={}",
//                    result_upsert_id,
//                    res.getMatchedCount(),
//                    res.getModifiedCount(),
//                    res.wasAcknowledged()
//            );
//            if ((result_upsert_id == null || result_upsert_id.isNull()) && id == null) {
//                // 插入失败了？
//            }
//            return Optional.ofNullable(result_upsert_id)
//                    .map(it -> it.isNull() ? null : it)
//                    .map(BsonValue::asString)
//                    .map(BsonString::toString)
//                    // 如果 result_upsert_id 为 null 的话 id 应当不为 null。
//                    // 如果 id 为 null 的话 result_upsert_id 应当不为 null。
//                    .or(() -> Optional.of(id).map(ObjectId::toString))
//                    .flatMap(it -> Mongos.findByIdFilterInvalidId(mongo, _clz_p(), it, _objectMapper()))
//                    .orElse(null);
//        } else {
//            throw new ClassCastException(this.getClass() + "#save param not instance of " +
//                    ReadObjectId.class.getSimpleName()
//                    + " , it's " + value.getClass() + " , value is " + value);
//        }
    }

    /**
     * 警告：如果{@link TV}是{@link TP}的子类，则注意要在{@link #toPersistence(Object)}过滤掉视图层的脏数据！
     */
    @SuppressWarnings("NullableProblems")
    @SneakyThrows
    @Override
    @org.springframework.lang.Nullable
    public @Nullable TV save(@Nonnull TV value) {
        log.debug("[crudEndpoint] save - this.class={} , value is {}", this.getClass(), value);
        var tp = toPersistence(value);
        if (_config().warningSubclassOnSave && tp == value) {
            // 如果直接传视图层的对象，很容易会导致存储了视图层的脏数据，更有甚者可以覆写一些隐藏字段，例如密码。
            log.warn("【危】在 {}#toPersistence 中传入 参数自身 作为 子类对象 是不对滴，" +
                            "最起码应该 `Jsons.beanCopy(objectMapper, value, {}.class)` ！ " +
                            "value is {}",
                    _clz_p().getSimpleName(), this.getClass().getName(), value
            );
            // 这里保险起见克隆了一下。
            tp = Jsons.beanCopy(_objectMapper(), value, _clz_p());
        }
        var saved = _save(tp);
        if (saved == null) {
            return null;
        }
        return toView(saved);
    }

    @Override
    public void delete(@Nullable String s) {
        log.debug("[crudEndpoint] delete - this.class={} , _id is {}", this.getClass(), s);
        _mongo().remove(query(Criteria.where("_id").is(s)), _clz_p());
    }

    @SneakyThrows
    @Override
    public @Nonnull List<@Nonnull TV> list(Pageable pageable,
                                           @Nullable
                                           @org.springframework.lang.Nullable
                                           Filter filter) {
        var comment = "[crudEndpoint] list - this.class=" + this.getClass() + " , pageable="
                + pageable + " , filter=" + filter;
        log.debug(comment);
        var mongo = _mongo();
        var result = new ArrayList<TV>(pageable.isPaged() ? pageable.getPageSize() : 32);
        try (var c = Optional.of(
                        Mongos.getCollection(mongo, _clz_p()).find()
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
                        int maxPageSize = _config().maxPageSize;
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
                .comment(comment)
                .cursor()) {
            while (c.hasNext()) {
                var obj = c.next();
                String item = null;
                TP res1 = null;
                TV res2 = null;
                try {
                    item = obj.toJson();
                    res1 = _objectMapper().readValue(item, _clz_p());
                    res2 = toView(res1);
                } finally {
                    log.debug("[crudEndpoint]     item result : {}\t\t\t\t===>\t\t\t\t{}\t\t\t\t===>\t\t\t\t{}",
                            item,
                            res1,
                            Objects.equals(res2, res1) ? "(equal to prev)" : res2);
                }
                result.add(res2);
            }
            return result;
        }
    }

    protected FilterToBsonValueMapper _filter_to_bson_value_mapper() {
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
