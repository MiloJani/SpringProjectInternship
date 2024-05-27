package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.dto.request.ProjectEmployeeDTO;
import com.example.demo.dataproviders.entities.Projects;

import java.util.List;

public interface ProjectService {

    List<Projects> getAllProjects();

    ProjectDTO getProjectById(Integer id);

    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO updateProject(ProjectDTO projectDTO,Integer id);

    Integer deleteProject(Integer id);

//    Integer addEmployeeToProject(Integer projectId, Employees employee);
    ProjectEmployeeDTO addEmployeeToProject(Integer projectId, Integer employeeId);
}
