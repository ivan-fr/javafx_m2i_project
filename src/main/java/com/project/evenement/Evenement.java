package com.project.evenement;

import java.sql.Timestamp;

public abstract class Evenement {

    protected int id;
    protected String nom;
    protected Timestamp dateEvenement;
    protected String lieu;
    protected int placesDisponibles;
    protected double prix;
    protected String type; // "CONCERT", "SPECTACLE", "CONFERENCE"

    public Evenement(int id, String nom, Timestamp dateEvenement, String lieu,
                     int placesDisponibles, double prix, String type) {
        this.id = id;
        this.nom = nom;
        this.dateEvenement = dateEvenement;
        this.lieu = lieu;
        this.placesDisponibles = placesDisponibles;
        this.prix = prix;
        this.type = type;
    }

    public Evenement(String nom, Timestamp dateEvenement, String lieu,
                     int placesDisponibles, double prix, String type) {
        this.nom = nom;
        this.dateEvenement = dateEvenement;
        this.lieu = lieu;
        this.placesDisponibles = placesDisponibles;
        this.prix = prix;
        this.type = type;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    
    public Timestamp getDateEvenement() { return dateEvenement; }
    public void setDateEvenement(Timestamp dateEvenement) { this.dateEvenement = dateEvenement; }
    
    
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    
    
    public int getPlacesDisponibles() { return placesDisponibles; }
    public void setPlacesDisponibles(int placesDisponibles) { this.placesDisponibles = placesDisponibles; }
    
    
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    
    public String getType() { return type; }

   

    @Override
    public String toString() {
        return type + " { " +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + dateEvenement +
                ", lieu='" + lieu + '\'' +
                ", places=" + placesDisponibles +
                ", prix=" + prix +
                " }";
    }
}
