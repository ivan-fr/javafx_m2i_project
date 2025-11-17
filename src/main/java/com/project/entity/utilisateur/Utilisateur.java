package com.project.entity.utilisateur;

import java.sql.Timestamp;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private String typeCompte; // 'CLIENT' ou 'ORGANISATEUR'
    private Timestamp dateCreation;

    // Constructeur par défaut
    public Utilisateur() {
    }

    // Constructeur sans ID (ID auto-généré par la BDD)
    public Utilisateur(String nom, String email, String motDePasse, String typeCompte) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeCompte = typeCompte;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    // Méthode toString pour faciliter l'affichage
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", typeCompte='" + typeCompte + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
