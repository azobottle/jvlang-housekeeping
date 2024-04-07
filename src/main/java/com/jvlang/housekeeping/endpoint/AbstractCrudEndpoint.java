package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.pojo.exceptions.CrudFailed;
import com.jvlang.housekeeping.util.Utils;
import dev.hilla.EndpointExposed;
import dev.hilla.Nullable;
import dev.hilla.crud.CrudRepositoryService;
import dev.hilla.crud.filter.Filter;
import lombok.Lombok;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import static com.jvlang.housekeeping.util.Utils.Throwables.findAnyCauseInstanceOf;

@EndpointExposed
public abstract class AbstractCrudEndpoint<T, ID, R extends CrudRepository<T, ID> & JpaSpecificationExecutor<T>>
        extends CrudRepositoryService<T, ID, R> {
    private RuntimeException handleErrorDefault(Throwable err) {
        return Lombok.sneakyThrow(err);
    }

    protected List<T> listAll() {
        try {
            return Utils.Coll.arrayListFromIter(getRepository().findAll(), 80);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }

    @Override
    public @Nullable T save(T value) {
        try {
            return super.save(value);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }

    @Override
    public List<T> saveAll(Iterable<T> values) {
        try {
            return super.saveAll(values);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }

    @Override
    public void delete(ID id) {
        try {
            super.delete(id);
        } catch (Throwable err) {
            var e1 = findAnyCauseInstanceOf(err, java.sql.SQLIntegrityConstraintViolationException.class);
            if (e1 != null) {
                throw new CrudFailed("删除失败，该数据被其他数据所引用（外键约束）: " + err.getMessage(), err);
            }
            throw handleErrorDefault(err);
        }
    }

    @Override
    public void deleteAll(Iterable<ID> ids) {
        try {
            super.deleteAll(ids);
        } catch (Throwable err) {
            var e1 = findAnyCauseInstanceOf(err, java.sql.SQLIntegrityConstraintViolationException.class);
            if (e1 != null) {
                throw new CrudFailed("删除失败，该数据被其他数据所引用（外键约束）: " + err.getMessage(), err);
            }
            throw handleErrorDefault(err);
        }
    }

    @Override
    public List<T> list(Pageable pageable, @Nullable Filter filter) {
        try {
            return super.list(pageable, filter);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }

    @Override
    public Optional<T> get(ID id) {
        try {
            return super.get(id);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }

    @Override
    public boolean exists(ID id) {
        try {
            return super.exists(id);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }

    @Override
    public long count(@Nullable Filter filter) {
        try {
            return super.count(filter);
        } catch (Throwable err) {
            throw handleErrorDefault(err);
        }
    }
}
