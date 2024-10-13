package com.project.schoolmanagment.entity.concrets;


import com.project.schoolmanagment.entity.abstracts.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Dean extends User {
}
