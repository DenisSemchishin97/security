package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class MyController {
    private UserService userService;
    MyController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/user")
    public String showUserInformation(Principal principal, Model model){
        User user=userService.findByUserName(principal.getName());
        model.addAttribute("user",userService.findUserById(user.getId()));
        return "/user";
    }
}
