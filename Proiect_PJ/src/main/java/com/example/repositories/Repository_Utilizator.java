package com.example.repositories;

import com.example.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository_Utilizator extends JpaRepository<Client, Long> {
        Client findByUtilizator(String utilizator);
}
