package com.jvlang.housekeeping.repo;

import com.jvlang.housekeeping.pojo.entity.GoodCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodCatalogRepository extends JpaRepository<GoodCatalog, Long>,
        JpaSpecificationExecutor<GoodCatalog> {
}
