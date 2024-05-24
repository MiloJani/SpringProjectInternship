package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Integer> {
    Optional<Departments> findByDepartmentName(String name);
}
