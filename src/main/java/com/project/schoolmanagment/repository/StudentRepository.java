package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrets.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phone);

    Student findByUsernameEquals(String username);
}
