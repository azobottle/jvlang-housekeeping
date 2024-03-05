package com.example.application.data.repository.example;


import com.example.application.data.entity.example.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
