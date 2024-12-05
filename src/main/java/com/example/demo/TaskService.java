package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> searchTasks(String query) {
        return taskRepository.findByTitleContainingIgnoreCase(query); // Метод для поиска задач
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUser_UserId(userId);
    }

    public List<Task> getTasksByCategoryId(Long categoryId) {
        return taskRepository.findByCategory_CategoryId(categoryId);
    }


    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }


    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
    public Page<Task> getTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }

    public Page<Task> searchTasks(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByTitleContainingIgnoreCase(query, pageable);
    }
    public Page<Task> filterTasks(Long categoryId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (categoryId != null && status != null) {
            return taskRepository.findByCategory_CategoryIdAndStatus(categoryId, status, pageable);
        } else if (categoryId != null) {
            return taskRepository.findByCategory_CategoryId(categoryId, pageable);
        } else if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        } else {
            return taskRepository.findAll(pageable);
        }
    }


}

