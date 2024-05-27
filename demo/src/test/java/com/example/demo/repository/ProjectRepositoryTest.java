package com.example.demo.repository;

import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.dataproviders.repositories.ProjectsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProjectRepositoryTest {

    private ProjectsRepository projectsRepository;

    @Autowired
    public ProjectRepositoryTest(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    @Test
    public void projectRepository_saveAll_ReturnsSavedProject(){
        Projects projects = Projects.builder().project_id(1)
                .project_name("Spring Internship")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>()) //import EmployeesRepository,cretae employee and save
                .build();

        Projects savedProject=projectsRepository.save(projects);

        Assertions.assertThat(savedProject).isNotNull();
        Assertions.assertThat(savedProject.getProject_id()).isGreaterThan(0);
    }

    @Test
    public void projectRepository_GetAll_ReturnsMoreThanOneProject(){

        Projects project1 = Projects.builder().project_id(1)
                .project_name("Spring Internship")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>())
                .build();

        Projects project2 = Projects.builder().project_id(2)
                .project_name("Spring Internship 2")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>())
                .build();

        projectsRepository.save(project1);
        projectsRepository.save(project2);

        List<Projects> projectsList=projectsRepository.findAll();

        Assertions.assertThat(projectsList).isNotNull();
        Assertions.assertThat(projectsList.size()).isEqualTo(2);
    }

    @Test
    public void projectRepository_findById_ReturnsSavedProject(){
        Projects projects = Projects.builder().project_id(1)
                .project_name("Spring Internship")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>())
                .build();

        projectsRepository.save(projects);

        Projects project = projectsRepository.findById(projects.getProject_id()).get();

        Assertions.assertThat(project).isNotNull();

    }

    @Test
    public void projectRepository_updateProject_ReturnsProject(){
        Projects projects = Projects.builder().project_id(1)
                .project_name("Spring Internship")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>())
                .build();

        projectsRepository.save(projects);

        Projects projectSave = projectsRepository.findById(projects.getProject_id()).get();
        projectSave.setProject_name("Spring Internship1");
        Projects updateProject=projectsRepository.save(projectSave);

        Assertions.assertThat(updateProject.getProject_name()).isNotNull();
    }

    @Test
    public void projectRepository_deleteProject_returnProjectIsEmpty(){
        Projects projects = Projects.builder().project_id(1)
                .project_name("Spring Internship")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>())
                .build();
        projectsRepository.save(projects);

        projectsRepository.deleteById(projects.getProject_id());
        Optional<Projects> project = projectsRepository.findById(projects.getProject_id());

        Assertions.assertThat(project).isEmpty();
    }
}
