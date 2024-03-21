package com.jvlang.housekeeping.controller;

import com.alibaba.excel.EasyExcel;
import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.easyexcel.BaseExcelListener;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.entity.RelationShifuService;
import com.jvlang.housekeeping.pojo.entity.UserRole;
import com.jvlang.housekeeping.pojo.entity.Schedule;
import com.jvlang.housekeeping.repo.RelationShifuServiceRepository;
import com.jvlang.housekeeping.repo.UserRoleRepository;
import com.jvlang.housekeeping.repo.ScheduleRepository;
import com.jvlang.housekeeping.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/api/file")
@Slf4j
public class FileController {
    private final String SCHEDULE = "schedule";
    private final String RELA_USER_ROLE = "rela_user_role";
    private final String RELA_SHIFU_SERVICE = "rela_shifu_service";
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RelationShifuServiceRepository relationShifuServiceRepository;
    @Autowired
    private UserUtils userUtils;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file, @RequestHeader("type") String type) throws IOException {
        var jwtUser = userUtils.readUser();
        assert jwtUser != null;
        var roles = userRoleRepository.findRolesByUserId(jwtUser.getUserId());
        if (!(roles.contains(Role0.SuperAdmin) || roles.contains(Role0.Manager))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("角色不是超管或主管");
        }
        switch (type) {
            case SCHEDULE -> EasyExcel.read(file.getInputStream(), Schedule.class,
                    new BaseExcelListener<>(scheduleRepository)).sheet().doRead();
            case RELA_USER_ROLE -> EasyExcel.read(file.getInputStream(), UserRole.class,
                    new BaseExcelListener<>(userRoleRepository)).sheet().doRead();
            case RELA_SHIFU_SERVICE -> EasyExcel.read(file.getInputStream(), RelationShifuService.class,
                    new BaseExcelListener<>(relationShifuServiceRepository)).sheet().doRead();
        }
        return ResponseEntity.status(HttpStatus.OK).body("成功");
    }
}
