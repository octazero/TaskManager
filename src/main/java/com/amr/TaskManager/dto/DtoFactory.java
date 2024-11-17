package com.amr.TaskManager.dto;


import com.amr.TaskManager.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoFactory {
    public TaskDto createTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .build();
    }
    public List<TaskDto> createTaskDtoList(List<Task> tasks) {
        return tasks.stream().map(this::createTaskDto).toList();
    }
}
