package com.project.entity.evenement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class Evenement {
    private int id; // id en BDD
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int organisateurId; // FK vers utilisateurs (ORGANISATEUR)
    private List<CategoryPlace> categories = new ArrayList<>();

    public Evenement(String nom, LocalDateTime date, String lieu) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    public Evenement(int id,String nom, LocalDateTime date, String lieu) {
        this.id= id;
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

    public int getOrganisateurId() {
        return organisateurId;
    }

    public void setOrganisateurId(int organisateurId) {
        this.organisateurId = organisateurId;
    }

    public List<CategoryPlace> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryPlace> categories) {
        this.categories = categories;
    }

    public void ajouterCategorie(CategoryPlace c) {
        categories.add(c);
    }

    public CategoryPlace getCategorieParNom(String nom) {
        return categories.stream().filter(c -> c.getCategorie().equals(nom)).findFirst().orElse(null);
    }

}






