package com.example.services;

import com.example.entities.Masina;
import com.example.entities.Client;
import com.example.repositories.Repository_Masina;
import com.example.repositories.Repository_Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Service
public class Service_Masina {

    @Autowired //ofera automat dependentele din repository
    private Repository_Masina repositoryMasina;
    @Autowired
    private Repository_Utilizator repositoryUtilizator;


    public List<Masina> findAll() {
        return repositoryMasina.findAll();
    } //toate datele din BD

    //se iau toate cazurile posibile pt filtrare
    public List<Masina> filterCars(String nrInmatriculare, String tipCombustibil, Integer anFabricatie) {
        if(nrInmatriculare == null && tipCombustibil == null && anFabricatie == null) {
            return repositoryMasina.findAll(); //se returneaza tot
        }
        else if(!nrInmatriculare.isEmpty() && tipCombustibil.isEmpty() && anFabricatie == null) {
            return repositoryMasina.findCarsByCriteria(nrInmatriculare, null, null);
        }
        else if(nrInmatriculare.isEmpty() && !tipCombustibil.isEmpty() && anFabricatie == null)
        {
            return repositoryMasina.findCarsByCriteria(null, tipCombustibil, null);
        }
        else if(nrInmatriculare.isEmpty() && tipCombustibil.isEmpty() && anFabricatie != null)
        {
            return repositoryMasina.findCarsByCriteria(null, null, anFabricatie);
        }
        else if(!nrInmatriculare.isEmpty() && !tipCombustibil.isEmpty() && anFabricatie == null)
        {
            return repositoryMasina.findCarsByCriteria(nrInmatriculare, tipCombustibil, null);
        }
        else if(!nrInmatriculare.isEmpty() && tipCombustibil.isEmpty() && anFabricatie != null)
        {
            return repositoryMasina.findCarsByCriteria(nrInmatriculare, null, anFabricatie);
        }
        else if(nrInmatriculare.isEmpty() && !tipCombustibil.isEmpty() && anFabricatie != null)
        {
            return repositoryMasina.findCarsByCriteria(null, tipCombustibil, anFabricatie);
        }
        else if(!nrInmatriculare.isEmpty())
        {
            return repositoryMasina.findCarsByCriteria(nrInmatriculare, tipCombustibil, anFabricatie);
        }
        else {
            return repositoryMasina.findAll();
        }
    }

    //FUNCTII FOLOSITE IN CONTROLLER

    //folosit pentru editare
    public Masina findByNrInmatriculare(String nrInmatriculare) {
        return repositoryMasina.findById(nrInmatriculare).orElse(null);
    }

    //folosit pentru salvare, dupa modificarile facute
    public void save(Masina masina)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //afla cine s-a logat
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername(); //extrage username-ul
            Client client = repositoryUtilizator.findByUtilizator(username);
            masina.setClient(client); //salvam masina pe numele lui
        }
        repositoryMasina.save(masina); //salvam in BD
    }

    //folosit pentru stergere
    public void deleteByNrInmatriculare(String nrInmatriculare) {
        repositoryMasina.deleteById(nrInmatriculare);
    }
}
