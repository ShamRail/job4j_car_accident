package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("names", List.of(
                "Ivan",
                "Boris",
                "Boris",
                "Petr",
                "Michael"
        ));
        return "index";
    }

}
