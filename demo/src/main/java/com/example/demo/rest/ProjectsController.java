package com.example.demo.rest;

import com.example.demo.entities.Projects;
import com.example.demo.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    private ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    ResponseEntity<List<Projects>> getAllProjects(){

        List<Projects> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<String> createProject(@RequestBody Projects project){

        Integer id = projectService.createProject(project);
        return ResponseEntity.ok("Projekti me id: "+id+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<Projects> updateProject(@RequestBody Projects project,@PathVariable Integer id){

        Projects updatedProject = projectService.updateProject(project,id);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProject(@PathVariable Integer id){

        Integer projectId = projectService.deleteProject(id);

        return ResponseEntity.ok("Projekti me id: "+id+" u fshi");
    }
}
