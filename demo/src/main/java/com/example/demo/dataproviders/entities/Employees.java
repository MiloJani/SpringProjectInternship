package com.example.demo.dataproviders.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO,TABLE,IDENTITY,SEQUENCE
    private Integer employee_id;

    //default:length-255,nullable-true,unique-false
    //updatable,insertable
    @Column(name="first_name", length=20, nullable=false, unique=false)
    @NotBlank(message = "Field is required")
    private String first_name;

    @Column(name = "last_name", length = 20,nullable = false)
    private String last_name;

    @Column(name = "job_title", length = 100, nullable = false)
    private String job_title;

    private int salary;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonBackReference
    @JoinColumn(name = "department_id")
    private Departments department;

    @ManyToMany(mappedBy = "employees")
    private List<Projects> projects;
}
