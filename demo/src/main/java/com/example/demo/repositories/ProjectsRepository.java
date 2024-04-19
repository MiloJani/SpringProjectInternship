package com.example.demo.repositories;

import com.example.demo.entities.Projects;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends CrudRepository<Projects,Long> {
}
