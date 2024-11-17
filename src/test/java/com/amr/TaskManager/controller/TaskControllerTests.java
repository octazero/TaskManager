package com.amr.TaskManager.controller;

import com.amr.TaskManager.dto.TaskDto;
import com.amr.TaskManager.exceptions.TaskNotFoundException;
import com.amr.TaskManager.model.Task;
import com.amr.TaskManager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@MockBean(TaskService.class)
@AutoConfigureMockMvc
public class TaskControllerTests {

    public final TaskService taskService;
    private TaskDto givenTask;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        givenTask =  new TaskDto(
                1l,
                "Save Button not responding"
        );
    }
    @Test
    void getAllTasks() throws Exception {
        List<Task> tasks = asList(new Task(1l, "Save Button not responding"), new Task(2l, "Save Button not responding"));
        when(taskService.getAll()).thenReturn(tasks);
        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andExpectAll(
                    jsonPath("$",hasSize(2)),
                    jsonPath("$[0].title").value("Save Button not responding"),
                    jsonPath("$[0].id").value(1l),
                    jsonPath("$[1].title").value("Save Button not responding"),
                    jsonPath("$[1].id").value(2l)
                );
    }

    @Test
    void getAlltaksEmpty() throws Exception {
        when(taskService.getAll()).thenReturn(emptyList());
        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$",hasSize(0))
                );
    }
    @Test
    void getTaskById() throws Exception {
        when(taskService.getTaskById(Mockito.anyLong())).thenReturn(new Task(1l, "Save Button not responding"));
        mockMvc.perform(get("/api/v1/task/{id}", 1l))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(givenTask.getId()),
                        jsonPath("$.title").value(givenTask.getTitle())
                );
    }
    @Test
    void getTaskByIdNotFound() throws Exception {
        when(taskService.getTaskById(Mockito.anyLong())).thenThrow(new TaskNotFoundException(1l));
        mockMvc.perform(get("/api/v1/task/{id}", 1l))
                .andExpect(status().isNotFound())
                .andExpectAll(
                        jsonPath("$.message").value("Task with id: 1 not found")
                );
    }

    @Test
    void createTask() throws Exception {
        when(taskService.createTask(any())).thenReturn(new Task(1l, "Save Button not responding"));

        mockMvc.perform(post("/api/v1/task" )
                        .content(objectMapper.writeValueAsBytes(givenTask))
                        .contentType("application/json")
                )
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").value(givenTask.getId()),
                        jsonPath("$.title").value(givenTask.getTitle())
                );
    }

    @Test
    void updateTask() throws Exception {
        when(taskService.updateTask(anyLong(),any())).thenReturn(new Task(1l, "Save Button not responding"));

        mockMvc.perform(put("/api/v1/task/{id}",givenTask.getId() )
                        .content(objectMapper.writeValueAsBytes(givenTask))
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(givenTask.getId()),
                        jsonPath("$.title").value(givenTask.getTitle())
                );
    }

    @Test
    void updateTaskNotFound() throws Exception {
        when(taskService.updateTask(anyLong(),any())).thenThrow(new TaskNotFoundException(1l));

        mockMvc.perform(put("/api/v1/task/{id}",givenTask.getId() )
                        .content(objectMapper.writeValueAsBytes(givenTask))
                        .contentType("application/json")
                )
                .andExpect(status().isNotFound())
                .andExpectAll(
                        jsonPath("$.message").value("Task with id: 1 not found")
                );
    }

    @Test
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong());
        mockMvc.perform(delete("/api/v1/task/{id}", givenTask.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTaskNotFound() throws Exception {
        doThrow(new TaskNotFoundException(1l)).when(taskService).deleteTask(anyLong());
        mockMvc.perform(delete("/api/v1/task/{id}", givenTask.getId() ))
                .andExpect(status().isNotFound())
                .andExpectAll(
                        jsonPath("$.message").value("Task with id: 1 not found")
                );
    }
}
