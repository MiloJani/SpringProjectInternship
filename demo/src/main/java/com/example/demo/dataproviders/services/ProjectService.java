package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.dto.ProjectDTO;
import com.example.demo.dataproviders.entities.Projects;

import java.util.List;

public interface ProjectService {

    List<Projects> getAllProjects();

    Projects getProjectById(Integer id);

    Integer createProject(ProjectDTO projectDTO);

    ProjectDTO updateProject(ProjectDTO projectDTO,Integer id);

    Integer deleteProject(Integer id);

    Integer addEmployeeToProject(Integer projectId, Employees employee);
//    Integer addEmployeeToProject(Integer projectId, Integer employeeId);
}
