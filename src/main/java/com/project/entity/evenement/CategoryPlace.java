package com.project.entity.evenement;


public class CategoryPlace {
    private int id; // pour la BDD
    private int evenementId; // FK vers evenements
    private String categorie;
    private int nbPlaces;
    private double prix;

    public CategoryPlace(String categorie, int nbPlaces, double prix) {
        this.categorie = categorie;
        this.nbPlaces = nbPlaces;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
