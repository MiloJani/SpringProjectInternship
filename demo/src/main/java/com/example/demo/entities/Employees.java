package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

//@Data -getter,setter,requiredconstructor,toString,hashcode
//@Builder -no need for new -> ClassName.builder()
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO,TABLE,IDENTITY,SEQUENCE
    private int employee_id;

    //default:length-255,nullable-true,unique-false
    //updatable,insertable
    @Column(name="FIRST_NAME", length=20, nullable=false, unique=false)
    @NotBlank(message = "Field is required")
    private String first_name;

    @Column(name = "LAST_NAME", length = 20,nullable = false)
    private String last_name;

    @Column(name = "JOB_TITLE", length = 100, nullable = false)
    private String job_title;

    private int salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departments department;

    @ManyToMany(mappedBy = "employees")
    private List<Projects> projects;
}
