package com.amr.TaskManager.service;

import com.amr.TaskManager.dto.TaskDto;
import com.amr.TaskManager.exceptions.TaskNotFoundException;
import com.amr.TaskManager.model.Task;
import com.amr.TaskManager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@MockBean(TaskRepository.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TaskServiceTests {


    private final TaskRepository taskRepository;
    private final TaskService taskService;


    @Test
    void getTaskByIdTaskExistsReturnsTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
    }

    @Test
    void getTaskByIdTaskDoesNotExistThrowsException() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
        assertEquals("Task with id: 1 not found", ex.getMessage());
    }

    @Test
    void getAllReturnsListOfTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void createTaskSuccessfullyCreatesTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test Task");
        Task task = new Task(taskDto);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(taskDto);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void updateTaskTaskExistsUpdatesAndReturnsTask() {
        Long taskId = 1L;
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Updated Title");
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Original Title");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.updateTask(taskId, taskDto);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void updateTaskTaskDoesNotExistThrowsException() {
        Long taskId = 1L;
        TaskDto taskDto = new TaskDto();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskDto));
        assertEquals("Task with id: 1 not found", ex.getMessage());
    }

    @Test
    void deleteTaskTaskExistsDeletesTask() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteTaskTaskDoesNotExistThrowsException() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(false);


        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
        assertEquals("Task with id: 1 not found", ex.getMessage());
    }
}
