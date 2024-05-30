package com.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "utilizatori")
@Data
public class Client {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) //generata automat
        private Long id;

        private String nume;
        private String utilizator;
        private String parola;
        private String rol;

        @OneToMany(mappedBy = "client") //un client poate avea mai multe masini
        private List<Masina> masini;
}
