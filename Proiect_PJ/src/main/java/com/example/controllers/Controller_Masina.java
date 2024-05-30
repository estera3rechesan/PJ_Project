package com.example.controllers;

import com.example.entities.Masina;
import com.example.entities.Client;
import com.example.services.Service_Masina;
import com.example.services.Service_Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/homepage") //totate cererile din controller tb sa inceapa cu /homepage -> asta insemna ca esti deja logat
public class Controller_Masina {

    @Autowired
    private Service_Masina serviceMasina;
    private final Service_Utilizator serviceUtilizator;
    public Controller_Masina(Service_Utilizator serviceUtilizator) {
        this.serviceUtilizator = serviceUtilizator;
    }

    //mod (de tip Model, din Spring MVC) - transmite date intre controller si html

    @PreAuthorize("hasRole('ROLE_EDITOR')") //restrictionat editor
    @GetMapping("/editor")
    public String viewCars(@RequestParam(required = false) String nrInmatriculare,
                           @RequestParam(required = false) String tipCombustibil,
                           @RequestParam(required = false) Integer anFabricatie,
                           Model mod) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //se preiau date despre userul curent
        Client client = serviceUtilizator.findByUtilizator(userDetails.getUsername());
        mod.addAttribute("name", client.getNume()); //pt Bun venit
        mod.addAttribute("cars", serviceMasina.filterCars(nrInmatriculare, tipCombustibil, anFabricatie)); //pt filtrare
        return "pagina_editor";
    }

    @PreAuthorize("hasRole('ROLE_USER')") //restrictionat user
    @GetMapping("/user")
    public String viewCarsUser(@RequestParam(required = false) String nrInmatriculare,
                               @RequestParam(required = false) String tipCombustibil,
                               @RequestParam(required = false) Integer anFabricatie,
                               Model mod) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = serviceUtilizator.findByUtilizator(userDetails.getUsername());
        mod.addAttribute("name", client.getNume());
        mod.addAttribute("cars", serviceMasina.filterCars(nrInmatriculare, tipCombustibil, anFabricatie));
        return "pagina_user";
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("/add-car")
    public String addCarForm(Model model) {
        model.addAttribute("car", new Masina()); //obiect folosit la adaugare
        return "adaugare";
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Masina masina) {
        serviceMasina.save(masina); //se salveaza masina in BD
        return "redirect:/homepage/editor";
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("/edit-car/{nrInmatriculare}")
    public String editCarForm(@PathVariable String nrInmatriculare, Model model) {
        model.addAttribute("car", serviceMasina.findByNrInmatriculare(nrInmatriculare)); //se ia masina dupa nrInmatriculare pt editare
        return "editare";
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PostMapping("/edit-car")
    public String editCar(@ModelAttribute Masina masina) {
        serviceMasina.save(masina); //se salveaza modificarile
        return "redirect:/homepage/editor";
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("/editor/{nrInmatriculare}")
    public String deleteCar(@PathVariable String nrInmatriculare) {
        serviceMasina.deleteByNrInmatriculare(nrInmatriculare); //stergere
        return "redirect:/homepage/editor";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
}
