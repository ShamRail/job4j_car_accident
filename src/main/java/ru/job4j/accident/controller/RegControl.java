package ru.job4j.accident.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.jpa.User;
import ru.job4j.accident.repo.jpa.AuthorityRepo;
import ru.job4j.accident.repo.jpa.UserRepo;

@Controller
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserRepo users;
    private final AuthorityRepo authorities;

    public RegControl(PasswordEncoder encoder, UserRepo users, AuthorityRepo authorities) {
        this.encoder = encoder;
        this.users = users;
        this.authorities = authorities;
    }

    @PostMapping("/reg")
    public String save(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String reg(@ModelAttribute Accident accident) {
        return "reg";
    }
}
