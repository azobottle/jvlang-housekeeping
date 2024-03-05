package com.example.application.persistence.repository.example;


import java.util.List;

import com.example.application.persistence.entity.example.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c JOIN FETCH c.company")
    List<Contact> findAllWithCompany();
}
