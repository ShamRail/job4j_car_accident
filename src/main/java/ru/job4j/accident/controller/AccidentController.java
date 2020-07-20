package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class AccidentController {

    private final AccidentService service;

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
        accident.setRuleAccidents(service.mapRules(ids, accident));
        service.saveOrUpdate(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("rules", service.findAllRules().stream().map(r -> Rule.of(r.getId(), r.getName())).collect(Collectors.toSet()));
        model.addAttribute("types", service.findAllTypes().stream().map(t -> AccidentType.of(t.getId(), t.getName())).collect(Collectors.toSet()));
        model.addAttribute("accident", service.findAccidentWithRules(id));
        return "update";
    }

}
