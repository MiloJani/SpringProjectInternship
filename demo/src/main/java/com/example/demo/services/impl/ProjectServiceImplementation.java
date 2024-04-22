package com.example.demo.services.impl;

import com.example.demo.entities.Projects;
import com.example.demo.repositories.ProjectsRepository;
import com.example.demo.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private ProjectsRepository projectsRepository;

    public ProjectServiceImplementation(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    @Override
    public List<Projects> getAllProjects() {

        return (List<Projects>) projectsRepository.findAll();
    }

    @Override
    public Integer createProject(Projects project) {

        Projects newProject = new Projects();
        newProject.setProject_id(project.getProject_id());
        newProject.setProject_name(project.getProject_name());
        newProject.setStart_date(project.getStart_date());
        newProject.setEnd_date(project.getEnd_date());
        projectsRepository.save(newProject);
        return newProject.getProject_id();
    }

    @Override
    public Projects updateProject(Projects project, Integer id) {

        Optional<Projects> projects = projectsRepository.findById(id);

        //Duam te bejme update vetem emrin
        if (projects.isPresent()){
            Projects updateProject = projects.get();
            updateProject.setProject_name(project.getProject_name());
            projectsRepository.save(updateProject);
            return updateProject;

        }
        return null;
    }

    @Override
    public Integer deleteProject(Integer id) {

        Optional<Projects> projects = projectsRepository.findById(id);

        if (projects.isPresent()){

            Projects deleteProject = projects.get();
            projectsRepository.delete(deleteProject);
            return id;
        }
        return null;
    }
}
