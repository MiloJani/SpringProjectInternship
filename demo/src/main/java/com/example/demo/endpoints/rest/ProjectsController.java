package com.example.demo.endpoints.rest;

import com.example.demo.dataproviders.dto.ProjectDTO;
import com.example.demo.dataproviders.entities.Employees;
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
    ResponseEntity<Projects> getProjectById(@PathVariable Integer id){

        Projects project = projectService.getProjectById(id);

        return ResponseEntity.ok(project);
    }

    @PostMapping
    ResponseEntity<String> createProject(@RequestBody ProjectDTO projectDTO){

        Integer id = projectService.createProject(projectDTO);
        return ResponseEntity.ok("Projekti me id: "+id+" u krijua");
    }

    @PostMapping("/{projectId}")
    ResponseEntity<String> addEmployeeToProject( @RequestBody Employees employee,@PathVariable Integer projectId){

        Integer id = projectService.addEmployeeToProject(projectId,employee);
        return ResponseEntity.ok("Employee me id: "+id+
                " u shtua ne projektin me id: "+projectId);
    }

//    @PostMapping("/{projectId}/{employeeId}")
//    ResponseEntity<String> addEmployeeToProject( @PathVariable Integer projectId,@PathVariable Integer employeeId){
//
//        Integer id = projectService.addEmployeeToProject(projectId,employeeId);
//        return ResponseEntity.ok("Employee me id: "+id+
//                " u shtua ne projektin me id: "+projectId);
//    }

    @PutMapping("/{id}")
    ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO,@PathVariable Integer id){

        ProjectDTO updatedProject = projectService.updateProject(projectDTO,id);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProject(@PathVariable Integer id){

        Integer projectId = projectService.deleteProject(id);

        return ResponseEntity.ok("Projekti me id: "+projectId+" u fshi");
    }
}
