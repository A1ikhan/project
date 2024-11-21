package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Метод для поиска задач по идентификатору пользователя
    List<Task> findByUser_UserId(Long userId);

    // Метод для поиска задач по идентификатору категории
    List<Task> findByCategory_CategoryId(Long categoryId);



}
