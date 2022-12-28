package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
@Controller
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String Info(Principal principal, Model model){
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("admin",userService.findUserById(user.getId()));
        return "/admin";
    }

    @RequestMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("AllUsers", userService.getAllUsers());
        return "/infoAboutUsers";
    }

    @RequestMapping("/admin/users/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
    @RequestMapping(value="/admin/users/new")
    public String addNewUser(Model model){
        User user=new User();
        model.addAttribute("user",user);

        return "/addNewUser";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user",userService.findUserById(id));
        return "/edit";
    }
    @PatchMapping("/admin/user/edit/admin/users/update/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable(name="id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
    }