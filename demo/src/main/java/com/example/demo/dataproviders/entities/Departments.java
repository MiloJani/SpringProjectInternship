package com.example.demo.dataproviders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "Departments",schema="public")
public class Departments {

    @Id
    private Integer department_id;

    private String department_name;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JsonManagedReference
    private List<Employees> employees;




}
