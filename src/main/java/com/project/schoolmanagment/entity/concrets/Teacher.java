package com.project.schoolmanagment.entity.concrets;

import com.project.schoolmanagment.entity.abstracts.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Teacher extends User {

    //TODO:learn about cascade types and orphanRemoval
    @OneToOne(mappedBy = "teacher" ,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private AdvisoryTeacher advisoryTeacher;

    @Column(name = "isAdvisor")
    private Boolean isAdvisory;

    @Column(unique = true)
    private String email;

    //TODO: learn database views

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;

    @ManyToMany
    @JoinTable(
            name = "teacher_lessonprogram",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
    )
    private Set<LessonProgram> lessonsProgramList;

}
