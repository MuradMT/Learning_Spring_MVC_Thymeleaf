package com.example.learning_spring_mvc_thymeleaf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        List<String> petNames = List.of("Fluffy", "Rex", "Whiskers", "Coco");
        model.addAttribute("petNames", petNames);
        return "index";
    }
}
