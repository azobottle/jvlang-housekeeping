package com.example.application.persistence.repository.example;


import com.example.application.persistence.entity.example.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
