package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // шаблон регистрации
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/login";
    }


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // Вы можете добавить любые дополнительные атрибуты в модель, если необходимо
        return "login";  // Это название HTML-шаблона для страницы входа, например, login.html
    }

    @GetMapping("/all")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // шаблон со списком пользователей
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user_detail"; // шаблон для отображения информации о пользователе
        } else {
            return "redirect:/users/all";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users/all";
    }

}
