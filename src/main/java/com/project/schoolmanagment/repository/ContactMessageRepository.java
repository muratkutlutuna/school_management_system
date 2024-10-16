package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrets.ContactMessage;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {

    /*
     * existsByEmailEqualsAndDateEquals(String email, LocalDate date)
     * This method checks if a contact message exists by comparing the email
     * and date values with the provided email
     * and date parameters. It uses the equals method for comparison.
     *
     * existsByEmailAndDate(String email, LocalDate date)
     * This method also checks if a contact message exists, but it compares the email
     * and date values using the default comparison behavior, which is generally equivalent
     * to using the == operator.
     *
     * So, the main difference lies in the comparison method used for the email
     * and date parameters. The first method (existsByEmailEqualsAndDateEquals) uses
     * the equal method, which compares the values of the two objects, while the second method
     * (existsByEmailAndDate) uses the default comparison behavior,
     * which compares the references of the two objects.
     *
     */


    boolean existsByEmailEqualsAndDateEquals(String email, LocalDate date);

    boolean existsByEmailAndDate(String email, LocalDate date);

    ContactMessage findByEmail(String email);

    Page<ContactMessage> findBySubjectEquals(String subject, Pageable myPageable);
}
