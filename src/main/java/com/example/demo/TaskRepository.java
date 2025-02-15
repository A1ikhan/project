package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Метод для поиска задач по идентификатору пользователя
    List<Task> findByUser_UserId(Long userId);

    // Метод для поиска задач по идентификатору категории
    List<Task> findByCategory_CategoryId(Long categoryId);

    List<Task> findByTitleContainingIgnoreCase(String title);

    Page<Task> findAll(Pageable pageable); // Пагинация для всех задач

    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Task> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    Page<Task> findByStatus(String status, Pageable pageable);

    Page<Task> findByCategory_CategoryIdAndStatus(Long categoryId, String status, Pageable pageable);
}
