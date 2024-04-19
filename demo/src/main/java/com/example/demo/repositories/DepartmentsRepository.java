package com.example.demo.repositories;

import com.example.demo.entities.Departments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepository extends CrudRepository<Departments, String> {}