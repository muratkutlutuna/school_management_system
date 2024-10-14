package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrets.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.repository.UserRoleRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType) {
//        Optional<UserRole> userRole = userRoleRepository.findByEnumRoleEquals(roleType);

        /**
         * check the Optional usage in springframework
         */
//        if (userRole.isPresent()) {
//            return userRole.get();
//        } else {
//            throw new ConflictException(Messages.ROLE_NOT_FOUND);
//        }
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(
                () -> new ConflictException(Messages.ROLE_NOT_FOUND));
    }




}
