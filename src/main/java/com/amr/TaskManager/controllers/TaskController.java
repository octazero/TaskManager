package com.amr.TaskManager.controllers;


import com.amr.TaskManager.configurations.MailConfiguration;
import com.amr.TaskManager.service.TaskService;
import com.amr.TaskManager.dto.DtoFactory;
import com.amr.TaskManager.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final DtoFactory dtoFactory;
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        TaskDto task = dtoFactory.createTaskDto(taskService.getTaskById(id));
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<TaskDto> list= dtoFactory.createTaskDtoList(taskService.getAll());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto task = dtoFactory.createTaskDto(taskService.createTask(taskDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto,@PathVariable Long id) {
        TaskDto task = dtoFactory.createTaskDto(taskService.updateTask(id,taskDto));
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
