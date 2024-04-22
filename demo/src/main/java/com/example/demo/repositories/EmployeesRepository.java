package com.example.demo.repositories;

import com.example.demo.entities.Employees;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends CrudRepository<Employees,Integer> {
}
