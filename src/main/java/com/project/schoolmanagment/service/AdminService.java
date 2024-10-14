package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrets.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.utils.Messages;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final DeanRepository deanRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GuestUserRepository guestUserRepository;
    private final UserRoleService userRoleService;

    public ResponseMessage save (AdminRequest adminRequest){
        checkDuplicate(adminRequest.getUsername(), adminRequest.getSsn(),adminRequest.getPhoneNumber());

        Admin admin = mapAdminRequestToAdmin(adminRequest);
        admin.setBuilt_in(false);

        //if username is also Admin we are setting built_in prop. to FALSE
        if (Objects.equals(adminRequest.getName(),"Admin")) {
            admin.setBuilt_in(false);
        }

        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        //we will implement password encoder here
        Admin savedAdmin = adminRepository.save(admin);
        //TODO: 03:04:49 6th video

    }

    private Admin mapAdminRequestToAdmin(AdminRequest adminRequest) {
        return Admin.builder()
                .userName(adminRequest.getUsername())
                .name(adminRequest.getName())
                .surname(adminRequest.getSurname())
                .password(adminRequest.getPassword())
                .ssn(adminRequest.getSsn())
                .birthDay(adminRequest.getBirthDay())
                .birthPlace(adminRequest.getBirthPlace())
                .phoneNumber(adminRequest.getPhoneNumber())
                .gender(adminRequest.getGender())
                .build();
    }

    //As a requirement all admin, ViceAdmin, Dean, Student, Teacher, guestUser
    //should have unique userName, email, ssn and phone number
    public void checkDuplicate(String username, String ssn, String phone) {
        if (adminRepository.existsByUsername(username)||
                deanRepository.existsByUsername(username)||
                studentRepository.existsByUsername(username)||
                teacherRepository.existsByUsername(username)||
                viceDeanRepository.existsByUsername(username)||
                guestUserRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
        } else if (adminRepository.existsBySsn(ssn) ||
                deanRepository.existsBySsn(ssn) ||
                studentRepository.existsBySsn(ssn) ||
                teacherRepository.existsBySsn(ssn) ||
                viceDeanRepository.existsBySsn(ssn) ||
                guestUserRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        } else if (adminRepository.existsByPhoneNumber(phone) ||
                deanRepository.existsByPhoneNumber(phone) ||
                studentRepository.existsByPhoneNumber(phone) ||
                teacherRepository.existsByPhoneNumber(phone) ||
                viceDeanRepository.existsByPhoneNumber(phone) ||
                guestUserRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
    }

}
