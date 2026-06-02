package com.safiyat.taskmanager.service.impl;

import com.safiyat.taskmanager.dto.TaskRequestDTO;
import com.safiyat.taskmanager.dto.TaskResponseDTO;
import com.safiyat.taskmanager.entity.Task;
import com.safiyat.taskmanager.entity.TaskStatus;
import com.safiyat.taskmanager.entity.User;
import com.safiyat.taskmanager.exception.ResourceNotFoundException;
import com.safiyat.taskmanager.exception.UnauthorizedException;
import com.safiyat.taskmanager.repository.TaskRepository;
import com.safiyat.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        User currentUser = getCurrentUser();

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .user(currentUser)
                .build();

        return mapToResponseDTO(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponseDTO> getAllTasks(int page, int size, String sortBy, TaskStatus status) {
        User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        if (status != null) {
            return taskRepository.findAllByUserAndStatus(currentUser, status, pageable)
                    .map(this::mapToResponseDTO);
        }

        return taskRepository.findAllByUser(currentUser, pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        return mapToResponseDTO(findTaskAndVerifyOwner(id));
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = findTaskAndVerifyOwner(id);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());

        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }

        return mapToResponseDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(findTaskAndVerifyOwner(id));
    }

    @Override
    public TaskResponseDTO completeTask(Long id) {
        Task task = findTaskAndVerifyOwner(id);
        task.setStatus(TaskStatus.COMPLETED);
        return mapToResponseDTO(taskRepository.save(task));
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private Task findTaskAndVerifyOwner(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(getCurrentUser().getId())) {
            throw new UnauthorizedException("You are not allowed to access this task");
        }
        return task;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .ownerUsername(task.getUser().getUsername())
                .build();
    }
}