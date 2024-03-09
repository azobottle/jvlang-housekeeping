package com.example.application.persistence;


import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Optional;

@SpringBootTest
public class TestPersistence {
//    @Autowired
//    private UserRepo userRepo;
//    @Autowired
//    private RoleRepo roleRepo;
//    @Autowired
//    private RelationUserRoleRepo relationUserRoleRepo;
//
//    private Date createdTime = new Date(System.currentTimeMillis());
//
//    private Date updatedTime = new Date(System.currentTimeMillis());
//
//    private Long createdBy = 1L;
//
//    private Long updatedBy = 1L;

//    @Test
//    void testUserRole() {//数据库已有一个用户，现添加一个角色，用户是这个角色，怎样在用户关系表中插入数据表示他们的关系
//        User user = new User();
//        user.setName("lzy");
//        user.setPassword("123456");
//        user.setPhoneNumber("138138");
//        User saveUser = userRepo.save(user);
//
//        Role admin = new Role();
//        admin.setName("admin");
//        admin.initHelpColumns(saveUser.getId());
//
//        Role saveAdmin = roleRepo.save(admin);
//
//        RelationUserRole relationUserRole = new RelationUserRole();
//        relationUserRole.setUserId(saveUser.getId());
//        relationUserRole.setRoleId(saveAdmin.getId());
//        relationUserRole.initHelpColumns(saveUser.getId());
//        relationUserRoleRepo.save(relationUserRole);
//
//        Optional<User> userOptional = userRepo.findOne(Example.of(user));
//        Optional<Role> roleOptional = roleRepo.findOne(Example.of(admin));
//        Assert.assertEquals(userOptional.orElse(null).getName(), "lzy");
//        Assert.assertEquals(roleOptional.orElse(null).getName(), "admin");

//    }
}
