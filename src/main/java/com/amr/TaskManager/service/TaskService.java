package com.amr.TaskManager.service;


import com.amr.TaskManager.dto.TaskDto;
import com.amr.TaskManager.exceptions.TaskNotFoundException;
import com.amr.TaskManager.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.TaskManager.repository.TaskRepository;

import java.util.List;

import static java.lang.Thread.sleep;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepisotry;

    @Cacheable(value = "task", key = "#id")
    public Task getTaskById(Long id){
        try{

            sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return taskRepisotry.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Cacheable("tasks")
    public List<Task> getAll(){
        try{

            sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return taskRepisotry.findAll();
    }

    @CacheEvict(value ="tasks" ,allEntries = true)
    public Task createTask(TaskDto taskDto){
        Task task =new Task(taskDto);
        return taskRepisotry.save(task);
    }

    @Caching(put = @CachePut(value = "task", key = "#id"),
           evict = @CacheEvict(value = "tasks", allEntries = true))
    public Task updateTask(Long id,TaskDto taskDto){
        Task task =getTaskById(id);
        task.setTitle(taskDto.getTitle());
        return taskRepisotry.save(task);
    }


    @Caching(evict = {
            @CacheEvict(value = "tasks", allEntries = true),
            @CacheEvict(value = "task", key = "#id")
    })
    public void deleteTask(Long id){
        if(!taskRepisotry.existsById(id)){
           throw new TaskNotFoundException(id);
        }
        taskRepisotry.deleteById(id);
    }
}
