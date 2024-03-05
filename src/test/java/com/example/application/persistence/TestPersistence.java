package com.example.application.persistence;


import com.example.application.persistence.repository.RoleRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestPersistence {
    @Autowired
    private RoleRepo roleRepo;
    @Test
    void test(){
    }
}
