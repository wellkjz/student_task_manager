package com.safiyat.taskmanager.service;

import com.safiyat.taskmanager.dto.TaskRequestDTO;
import com.safiyat.taskmanager.dto.TaskResponseDTO;
import com.safiyat.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO dto);

    Page<TaskResponseDTO> getAllTasks(int page, int size, String sortBy, TaskStatus status);

    TaskResponseDTO getTaskById(Long id);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO dto);

    void deleteTask(Long id);

    TaskResponseDTO completeTask(Long id);
}