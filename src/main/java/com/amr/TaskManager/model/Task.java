package com.amr.TaskManager.model;

import com.amr.TaskManager.dto.TaskDto;
import jakarta.persistence.*;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    public Task(TaskDto taskDto) {
        this.id = taskDto.getId();
        this.title = taskDto.getTitle();
    }
    public Task( String title) {
        this.title = title;
    }


    public Task() {

    }
}
