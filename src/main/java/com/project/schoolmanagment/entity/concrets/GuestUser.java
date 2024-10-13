package com.project.schoolmanagment.entity.concrets;

import com.project.schoolmanagment.entity.abstracts.User;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GuestUser extends User {
}
