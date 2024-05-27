package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.EmployeeIsAlreadyInProject;
import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.dto.request.ProjectEmployeeDTO;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.dataproviders.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    private final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    ResponseEntity<List<Projects>> getAllProjects(){

        List<Projects> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProjectDTO> getProjectById(@PathVariable Integer id) throws InvalidDataException, RecordNotFoundException {

        ProjectDTO project = projectService.getProjectById(id);

        return ResponseEntity.ok(project);
    }

    @PostMapping
    ResponseEntity<String> createProject(@RequestBody ProjectDTO projectDTO) throws RecordAlreadyExistsException {

        ProjectDTO project = projectService.createProject(projectDTO);
        return ResponseEntity.ok("Projekti me id: "+project.getProject_id()+" u krijua:"+project);
    }

//    @PostMapping("/{projectId}")
//    ResponseEntity<String> addEmployeeToProject( @RequestBody Employees employee,@PathVariable Integer projectId){
//
//        Integer id = projectService.addEmployeeToProject(projectId,employee);
//        return ResponseEntity.ok("Employee me id: "+id+
//                " u shtua ne projektin me id: "+projectId);
//    }

    @PostMapping("/addEmployee")
    ResponseEntity<String> addEmployeeToProject(@RequestBody ProjectEmployeeDTO projectEmployeeDTO) throws InvalidDataException,RecordNotFoundException, EmployeeIsAlreadyInProject {

        ProjectEmployeeDTO projectEmployeeDTO1 = projectService.addEmployeeToProject(projectEmployeeDTO.getProjectId(), projectEmployeeDTO.getEmployeeId());
        return ResponseEntity.ok("Employee me id: "+projectEmployeeDTO1.getEmployeeId()+
                " u shtua ne projektin me id: "+projectEmployeeDTO1.getProjectId());
    }

    @PutMapping("/{id}")
    ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO,@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        ProjectDTO updatedProject = projectService.updateProject(projectDTO,id);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProject(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer projectId = projectService.deleteProject(id);

        return ResponseEntity.ok("Projekti me id: "+projectId+" u fshi");
    }
}
