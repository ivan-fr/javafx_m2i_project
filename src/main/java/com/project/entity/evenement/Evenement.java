package com.project.entity.evenement;


import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Timestamp;


public abstract class Evenement {
    private int id; 
    private String nom;
    private Timestamp date;
    private String lieu;
    private int organisateurId; 
    private List<CategoryPlace> categories = new ArrayList<>();

    public Evenement(int id, String nom, Timestamp date, String lieu, int organisateurId) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.organisateurId = organisateurId;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
    
    private Evenement creerEvenementDepuisResultSet(ResultSet rs, String type) throws Exception {

        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        Timestamp date = rs.getTimestamp("date_evenement");
        String lieu = rs.getString("lieu");
        int organisateurId = rs.getInt("organisateur_id");

        switch (type.toUpperCase()) {
            case "CONCERT":
                return new Concert(id, nom, date, lieu, organisateurId);

            case "SPECTACLE":
                return new Spectacle(id, nom, date, lieu, organisateurId);

            case "CONFERENCE":
                return new Conference(id, nom, date, lieu, organisateurId);

            default:
                throw new Exception("Type d'événement inconnu : " + type);
        }
    }


    @Override
    public String toString() {
        // Texte qui sera affiché dans la ListView
        return nom + " - " + date.toLocalDate() + " - " + lieu;
    }

}







