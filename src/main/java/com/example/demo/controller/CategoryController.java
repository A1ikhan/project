package com.example.demo.controller;

import com.example.demo.Category;
import com.example.demo.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category_form"; // шаблон для добавления категории
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories/all";
    }

    @GetMapping("/all")
    public String showAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories"; // шаблон для отображения списка категорий
    }
}


