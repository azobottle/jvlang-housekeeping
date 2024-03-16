package com.jvlang.housekeeping.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseExcelListener<T, ID> implements ReadListener<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int BATCH_COUNT = 100;
    private final List<T> cachedDataList = new ArrayList<>(BATCH_COUNT);
    private final JpaRepository<T, ID> jpaRepository;

    public BaseExcelListener(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        try {
            log.info("解析到一条数据:{}", objectMapper.writeValueAsString(data));
            cachedDataList.add(data);
            if (cachedDataList.size() >= BATCH_COUNT) {
                saveData();
                cachedDataList.clear();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        jpaRepository.saveAll(cachedDataList);
        log.info("存储数据库成功！");
    }
}
