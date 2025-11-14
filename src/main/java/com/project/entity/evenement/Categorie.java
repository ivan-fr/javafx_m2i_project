package com.project.entity.evenement;


public class Categorie {
    private int id; // pour la BDD
    private String nom;
    private int nbPlaces;
    private double prix;

    public Categorie(String nom, int nbPlaces, double prix) {
        this.nom = nom;
        this.nbPlaces = nbPlaces;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
