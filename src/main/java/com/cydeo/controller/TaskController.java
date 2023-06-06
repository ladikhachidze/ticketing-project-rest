package com.cydeo.controller;


import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO> taskDTOS = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskDTOS, HttpStatus.OK));

    };

    @GetMapping("/{taskId}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long id){
        TaskDTO taskDTOS = taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",taskDTOS, HttpStatus.OK));

    };

    @PostMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is successfully created",HttpStatus.CREATED));

    };

    @DeleteMapping("/{taskId}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted", HttpStatus.OK));

    };

    @PutMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task ){
       taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", HttpStatus.OK));

    };

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> taskDTOS = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Not completed tasks are successfully retrieved",taskDTOS, HttpStatus.OK));

    };

    @PostMapping("/employee/update/")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("tasks are successfully updated", HttpStatus.OK));

    };

    @GetMapping("/employee/archive")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
        List<TaskDTO> taskDTOS = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("tasks are successfully retrieved",taskDTOS, HttpStatus.OK));

    };


}
