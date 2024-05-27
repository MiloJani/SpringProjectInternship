package com.example.demo.dataproviders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employees{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_id;

    @Column(name="first_name", length=20, nullable=false)
    @NotBlank(message = "Field is required")
    private String first_name;

    @Column(name = "last_name", length = 20,nullable = false)
    private String last_name;

    @Column(name = "job_title", length = 100, nullable = false)
    private String job_title;

    @Column(columnDefinition = "int default 5_000")
    private int salary;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonBackReference
//    @JsonIgnore
    @JoinColumn(name = "department_id")
    private Departments department;

    @ManyToMany(mappedBy = "employees")
//    @JsonIgnore
    private List<Projects> projects;


}
