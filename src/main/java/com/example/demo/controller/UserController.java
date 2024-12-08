package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

//    @GetMapping("/detail")
//    public String getUserDetail(Model model) {
//        Long userId = getCurrentUserId(); // Реализуйте метод для получения текущего пользователя
//        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        model.addAttribute("user", user);
//        return "user_detail"; // шаблон для отображения
//    }

    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users/{id}";
    }

    @PostMapping("/upload-photo")
    public String uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        Long userId = getCurrentUserId(); // Получение текущего пользователя
        userService.uploadPhoto(userId, photo);
        return "redirect:/users/{id}";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("current_password") String currentPassword,
                                 @RequestParam("new_password") String newPassword) {
        Long userId = getCurrentUserId();
        boolean isChanged = userService.changePassword(userId, currentPassword, newPassword);
        if (isChanged) {
            return "redirect:/users/{id}";
        } else {
            return "redirect:/users/{id}?error=password";
        }
    }

    private Long getCurrentUserId() {
        // Замените на логику для получения ID текущего пользователя (например, из Spring Security)
        return 1L;
    }
}
