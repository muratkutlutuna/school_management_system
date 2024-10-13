package com.project.schoolmanagment.entity.concrets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.schoolmanagment.entity.enums.Note;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * number of absentees of the student in the lessons
     */
    private Integer absentee;

    private Double midtermExam;

    private Double finalExam;

    private Double examAverage;

    private String infoNote;

    @ManyToOne
    @JsonIgnoreProperties("teacher")
    private Teacher teacher;

    @ManyToOne
    private Student student;

    @Enumerated(EnumType.STRING)
    private Note letterGrade;

    //TODO: 1:00:02 5th video

}
