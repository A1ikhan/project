package com.example.demo.controller;

import com.example.demo.CategoryService;
import com.example.demo.Task;
import com.example.demo.TaskService;
import com.example.demo.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final CategoryService categoryService;

    public TaskController(TaskService taskService, UserService userService, CategoryService categoryService) {
        this.taskService = taskService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.getAllUsers()); // Для выбора пользователя
        model.addAttribute("categories", categoryService.getAllCategories()); // Для выбора категории
        return "task_form"; // шаблон для добавления задачи
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.saveTask(task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/all")
    public String showAllTasks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {
        Page<Task> tasksPage = taskService.getTasks(page, size);
        model.addAttribute("tasks", tasksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasksPage.getTotalPages());
        return "tasks";
    }

    @GetMapping("/user/{userId}")
    public String getTasksByUserId(@PathVariable Long userId, Model model) {
        model.addAttribute("tasks", taskService.getTasksByUserId(userId)); // Используем обновленный метод из TaskService
        return "tasks_by_user"; // шаблон для задач по пользователю
    }

    @GetMapping("/category/{categoryId}")
    public String getTasksByCategoryId(@PathVariable Long categoryId, Model model) {
        model.addAttribute("tasks", taskService.getTasksByCategoryId(categoryId));
        return "tasks_by_category"; // шаблон для задач по категории
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return "redirect:/tasks/all";
    }

    @GetMapping("/search")
    public String searchTasks(@RequestParam("search") String query,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              Model model) {
        Page<Task> tasksPage = taskService.searchTasks(query, page, size);
        model.addAttribute("tasks", tasksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasksPage.getTotalPages());
        model.addAttribute("searchQuery", query);
        return "tasks";
    }
    @GetMapping("/filter")
    public String filterTasks(@RequestParam(required = false) Long categoryId,
                              @RequestParam(required = false) String status,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              Model model) {
        Page<Task> tasksPage = taskService.filterTasks(categoryId, status, page, size);
        model.addAttribute("tasks", tasksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasksPage.getTotalPages());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("categories", categoryService.getAllCategories()); // Для выбора категории
        return "tasks";
    }

}