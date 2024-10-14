package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrets.Dean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeanRepository extends JpaRepository<Dean, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phone);

    Dean findByUsernameEquals(String username);

}
