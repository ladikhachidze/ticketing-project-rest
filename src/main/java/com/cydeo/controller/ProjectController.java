package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;

import com.cydeo.service.ProjectService;
import com.cydeo.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjects(){

        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects has been retrieved ",projectDTOList,HttpStatus.OK));

    };

    @GetMapping("/{projectcode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("projectcode") String code){

        ProjectDTO projectDTO = projectService.getByProjectCode(code);

        return ResponseEntity.ok(new ResponseWrapper("Project has been retrieved successfully",projectDTO, HttpStatus.OK));


    };

    @PostMapping
    @RolesAllowed({"Admin","Manager"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project){
        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project has been created successfully",HttpStatus.CREATED));

    };

    @PutMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project){
        projectService.update(project);
        return ResponseEntity.ok(new ResponseWrapper("Project has been updated successfully",HttpStatus.OK));

    };

    @DeleteMapping("/{projectcode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectcode") String code){
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project has been deleted successfully",HttpStatus.OK));

    };

    @GetMapping("/manager/project-status")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjectByManager(ProjectDTO project){

        List<ProjectDTO> projectDTOList = projectService.listAllProjectDetails();

        return ResponseEntity.ok(new ResponseWrapper("Project details has been retrieved ",projectDTOList,HttpStatus.OK));

    };


    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String code){

        projectService.complete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project completed ",HttpStatus.OK));


    };


}
