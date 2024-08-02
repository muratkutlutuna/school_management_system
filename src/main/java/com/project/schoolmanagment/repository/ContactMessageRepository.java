package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrets.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {

    boolean existsByEmailEqualsAndDateEquals(String email, LocalDate date);

}
