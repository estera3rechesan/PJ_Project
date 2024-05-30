package com.example.services;

import com.example.entities.Client;
import com.example.repositories.Repository_Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Service_Utilizator implements UserDetailsService {

    private final Repository_Utilizator repositoryUtilizator;

    @Autowired
    private PasswordEncoder encoder; //variabila de tip PasswordEncoder

    @Autowired
    public Service_Utilizator(Repository_Utilizator repositoryUtilizator) {
        this.repositoryUtilizator = repositoryUtilizator;
    }

    //functie folosita in Controller_Utilizator -> la signup
    public void saveUser(Client client) {
        client.setRol("ROLE_USER");
        client.setParola(encoder.encode(client.getParola())); //cripteaza parola la creare cont
        repositoryUtilizator.save(client);
    }

    //functie folosita in Controller_Masina
    public Client findByUtilizator(String utilizator) {
        return repositoryUtilizator.findByUtilizator(utilizator);
    }


    //returneaza date despre utilizator in functie de username
    //folosita automat de security pentru a afla cine se logheaza, etc.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = repositoryUtilizator.findByUtilizator(username);
        if (client == null) {
            throw new UsernameNotFoundException("Utilizatorul nu a fost gasit!");
        }
        return new org.springframework.security.core.userdetails.User(client.getUtilizator(), client.getParola(), List.of(new SimpleGrantedAuthority(client.getRol())));
    }
}


