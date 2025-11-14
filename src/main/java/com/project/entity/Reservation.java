package com.project.entity;

import com.project.entity.evenement.Evenement;
import com.project.entity.utilisateur.Client;

import java.time.LocalDateTime;

public class Reservation {
    private Client client;
    private Evenement evenement;
    private String categorie;
    private int nbTickets;
    private LocalDateTime dateReservation;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getNbTickets() {
        return nbTickets;
    }

    public void setNbTickets(int nbTickets) {
        this.nbTickets = nbTickets;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

}