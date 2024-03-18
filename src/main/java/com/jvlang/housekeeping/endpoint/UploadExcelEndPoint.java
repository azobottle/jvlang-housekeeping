package com.jvlang.housekeeping.endpoint;

import com.alibaba.excel.EasyExcel;
import com.jvlang.housekeeping.easyexcel.BaseExcelListener;
import com.jvlang.housekeeping.pojo.entity.RelationShifuService;
import com.jvlang.housekeeping.pojo.entity.RelationUserRole;
import com.jvlang.housekeeping.pojo.entity.Schedule;
import com.jvlang.housekeeping.repo.RelationShifuServiceRepository;
import com.jvlang.housekeeping.repo.RelationUserRoleRepository;
import com.jvlang.housekeeping.repo.ScheduleRepository;
import dev.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@BrowserCallable
@RolesAllowed("ADMIN")
public class UploadExcelEndPoint {
    private final String SCHEDULE = "schedule";
    private final String RELA_USER_ROLE = "rela_user_role";
    private final String RELA_SHIFU_SERVICE = "rela_shifu_service";
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private RelationUserRoleRepository relationUserRoleRepository;
    @Autowired
    private RelationShifuServiceRepository relationShifuServiceRepository;

    public String upload(MultipartFile file, @RequestHeader("type") String type) throws IOException {
        switch (type) {
            case SCHEDULE -> EasyExcel.read(file.getInputStream(), Schedule.class,
                    new BaseExcelListener<>(scheduleRepository)).sheet().doRead();
            case RELA_USER_ROLE -> EasyExcel.read(file.getInputStream(), RelationUserRole.class,
                    new BaseExcelListener<>(relationUserRoleRepository)).sheet().doRead();
            case RELA_SHIFU_SERVICE -> EasyExcel.read(file.getInputStream(), RelationShifuService.class,
                    new BaseExcelListener<>(relationShifuServiceRepository)).sheet().doRead();
        }
        return "success";
    }
}