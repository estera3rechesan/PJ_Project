package com.example.entities;

import jakarta.persistence.*;
import lombok.Data;

//Entitati = clase mapate la tabelele din my sql
@Entity
@Table(name = "masini")
@Data //genereaza gettere si settere
public class Masina {
    @Id //cheia primara
    private String nrInmatriculare;

    @ManyToOne //mai multe masini pt acelasi client
    @JoinColumn(name = "id_utilizator")
    private Client client; //cel care a adaugat masina

    private String marca;
    private String model;
    private String culoare;
    private int anFabricatie;
    private int capacitateCilindrica;
    private String tipCombustibil;
    private int putere;
    private int cuplu;
    private int volumPortbagaj;
    private double pret;
}
