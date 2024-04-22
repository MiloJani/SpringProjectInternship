package com.example.demo.services;

import com.example.demo.entities.Projects;

import java.util.List;

public interface ProjectService {

    List<Projects> getAllProjects();

    Integer createProject(Projects project);

    Projects updateProject(Projects project,Integer id);

    Integer deleteProject(Integer id);
}
