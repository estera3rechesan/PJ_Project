package com.example.controllers;

import com.example.entities.Client;
import com.example.services.Service_Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Controller_Utilizator {
    private final Service_Utilizator serviceUtilizator;
    @Autowired
    public Controller_Utilizator(Service_Utilizator serviceUtilizator) {
        this.serviceUtilizator = serviceUtilizator;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String registerForm(Model model) {
        model.addAttribute("user", new Client()); //obiect folosit la creare cont
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute Client client) {
        serviceUtilizator.saveUser(client); //salveaza noul client in BD
        return "redirect:/login";
    }
}

//@GetMapping = cere resurse de la server (pag html)
//@PostMapping = trimite date la server (formular)