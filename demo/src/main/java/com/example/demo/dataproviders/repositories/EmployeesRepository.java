package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer> {
}
