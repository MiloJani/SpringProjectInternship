package com.example.demo.services.impl;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.entities.Employees;
import com.example.demo.entities.Projects;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.repositories.ProjectsRepository;
import com.example.demo.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectsRepository projectsRepository;

    public ProjectServiceImplementation(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    @Override
    public List<Projects> getAllProjects() {

        return projectsRepository.findAll();
    }

    @Override
    public Projects getProjectById(Integer id) {

        Optional<Projects> project = projectsRepository.findById(id);

        return project.orElseThrow(() -> new RecordNotFoundException(
                "Nuk u gjet projekt me kete id"));

    }

    @Override
    public Integer createProject(ProjectDTO projectDTO) {

        Projects newProject = mapToProjectEntity(projectDTO);
        projectsRepository.save(newProject);
        return newProject.getProject_id();
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO, Integer id) {

        Optional<Projects> projects = projectsRepository.findById(id);

        //Psh Duam te bejme update vetem emrin
        if (projects.isPresent()){
            Projects updateProject = projects.get();
            updateProject.setProject_name(projectDTO.getProject_name());
            Projects savedProject = projectsRepository.save(updateProject);
            return mapToProjectDTO(savedProject);

        }else throw new RecordNotFoundException(
                "Nuk u gjet projekt me kete id");
    }

    @Override
    public Integer deleteProject(Integer id) {

        Optional<Projects> projects = projectsRepository.findById(id);

        if (projects.isPresent()){

            Projects deleteProject = projects.get();
            projectsRepository.delete(deleteProject);
            return id;
        }else throw new RecordNotFoundException(
                "Nuk u gjet projekt me kete id");
    }

    @Override
    public Integer addEmployeeToProject(Integer projectId, Employees employee) {

        Optional<Projects> project = projectsRepository.findById(projectId);

        if (project.isPresent()){

            Projects foundProject = project.get();

            List<Employees> employees = foundProject.getEmployees();

            employees.add(employee);

            foundProject.setEmployees(employees);

            projectsRepository.save(foundProject);

            return employee.getEmployee_id();
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet projekt me kete id");
    }

    private ProjectDTO mapToProjectDTO(Projects project) {
        Integer id = project.getProject_id();
        String project_name = project.getProject_name();
        Date start_date = project.getStart_date();
        Date end_date = project.getEnd_date();

        return new ProjectDTO(id, project_name, start_date, end_date);
    }

    private Projects mapToProjectEntity(ProjectDTO projectDTO) {

        String projectName = projectDTO.getProject_name();
        Date start_date = projectDTO.getStart_date();
        Date end_date = projectDTO.getEnd_date();
        Projects project = new Projects();
        project.setProject_name(projectName);
        project.setStart_date(start_date);
        project.setEnd_date(end_date);
        return project;
    }

}
