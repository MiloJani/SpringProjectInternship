package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects,Integer> {
}
