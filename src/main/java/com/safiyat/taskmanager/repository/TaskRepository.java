package com.safiyat.taskmanager.repository;

import com.safiyat.taskmanager.entity.Task;
import com.safiyat.taskmanager.entity.TaskStatus;
import com.safiyat.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByUser(User user, Pageable pageable);

    Page<Task> findAllByUserAndStatus(User user, TaskStatus status, Pageable pageable);
}