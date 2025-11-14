package com.project.entity.evenement;

import com.project.entity.utilisateur.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Evenement {
    private int id; // id en BDD

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    private String nom;
    private LocalDateTime date;
    private String lieu;
    private List<Categorie> categories = new ArrayList<>();

    public Evenement(String nom, LocalDateTime date, String lieu) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void ajouterCategorie(Categorie c) {
        categories.add(c);
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public Categorie getCategorieParNom(String nom) {
        return categories.stream().filter(c -> c.getNom().equals(nom)).findFirst().orElse(null);
    }

    public void reserver(Client client, int nbTickets, String categorieNom) {
        Categorie cat = getCategorieParNom(categorieNom);
      /*  if (cat == null) throw new PlacesInsuffisantesException("Catégorie inexistante");
        if (nbTickets > cat.getNbPlaces()) throw new PlacesInsuffisantesException("Pas assez de places");*/
        cat.setNbPlaces(cat.getNbPlaces() - nbTickets);
        // Ajouter réservation au client
        System.out.println("Réservation effectuée pour " + nbTickets + " places en " + cat.getNom());
    }


}






