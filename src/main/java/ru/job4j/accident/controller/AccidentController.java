package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentController {

    private AccidentService service;

    public AccidentController(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", service.findAllTypes());
        model.addAttribute("rules", service.findAllRules());
        return "create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accident.setRules(service.mapRules(ids));
        service.saveOrUpdate(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", service.findById(id));
        return "update";
    }

}
