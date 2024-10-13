package com.project.schoolmanagment.entity.concrets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.abstracts.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Student extends User {

    private String motherName;

    private String fatherName;

    private int studentNumber;

    private boolean isActive;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JsonIgnore
    private AdvisoryTeacher advisoryTeacher;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<StudentInfo>studentInfos;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "student_lessonprogram",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn("lesson_program_id")
    )
    private Set<LessonProgram>lessonProgramList;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "meet_student_table",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name="meet_id")
    )
    private List<Meet> meetList;

}
