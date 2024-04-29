package com.example.demo.dataproviders.services.impl;

import com.example.demo.core.exceptions.RecordAlreadyExists;
import com.example.demo.dataproviders.entities.Employees;
//import com.example.demo.dataproviders.repositories.EmployeesRepository;
import com.example.demo.dataproviders.repositories.EmployeesRepository;
import com.example.demo.dataproviders.repositories.ProjectsRepository;
import com.example.demo.dataproviders.services.ProjectService;
import com.example.demo.dataproviders.dto.ProjectDTO;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.core.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectsRepository projectsRepository;
    private final EmployeesRepository employeesRepository;

    public ProjectServiceImplementation(ProjectsRepository projectsRepository, EmployeesRepository employeesRepository) {
        this.projectsRepository = projectsRepository;
        this.employeesRepository = employeesRepository;
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

        Projects existingProject = projectsRepository.findById(projectDTO.getProject_id()).orElse(null);

        if (existingProject==null) {

            Projects newProject = mapToProjectEntity(projectDTO);
            projectsRepository.save(newProject);
            return newProject.getProject_id();
        }
        else throw new RecordAlreadyExists("This project already exists");
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
//log.info
//    @Override
//    public Integer addEmployeeToProject(Integer projectId, Employees employee){ //mund te perdoret nje employeeDTO qe ka vtm id
//
//        Optional<Projects> project = projectsRepository.findById(projectId);
//
//        if (project.isPresent()){
//
//            Projects foundProject = project.get();
//
//            List<Employees> employees = foundProject.getEmployees();
//
//            if (!employees.contains(employee)) {
//
//                employees.add(employee);
//
//
//                foundProject.setEmployees(employees);
//
//                projectsRepository.save(foundProject);
//
//                return employee.getEmployee_id();
//            }else return -1; //exception employee already in project
//        }
//        else throw new RecordNotFoundException(
//                "Nuk u gjet projekt me kete id");
//    }

    @Override
    public Integer addEmployeeToProject(Integer projectId, Integer employeeId){ //mund te perdoret nje employeeDTO qe ka vtm id

        Optional<Projects> project = projectsRepository.findById(projectId);
        Optional<Employees> employee = employeesRepository.findById(employeeId);

        if (project.isPresent() && employee.isPresent()){

            Projects foundProject = project.get();

            Employees foundEmployee = employee.get();

            List<Employees> employees = foundProject.getEmployees();

            employees.add(foundEmployee);

            foundProject.setEmployees(employees);

            projectsRepository.save(foundProject);

            return foundProject.getProject_id();
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet projekt ose employee me kete id");
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
