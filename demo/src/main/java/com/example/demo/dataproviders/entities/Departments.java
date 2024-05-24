package com.example.demo.dataproviders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "Departments",schema="public")
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer department_id;

    private String departmentName;

    public Departments(Integer department_id, String department_name) {
        this.department_id = department_id;
        this.departmentName = department_name;
    }

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JsonManagedReference
    private List<Employees> employees;




}
