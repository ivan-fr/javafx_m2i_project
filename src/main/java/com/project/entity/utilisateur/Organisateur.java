package com.project.entity.utilisateur;

public class Organisateur extends Utilisateur {

    public Organisateur() {
        super();
        this.setTypeCompte("ORGANISATEUR");
    }

    public Organisateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse, "ORGANISATEUR");
    }

    @Override
    public String toString() {
        return "Organisateur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", typeCompte='" + getTypeCompte() + '\'' +
                '}';
    }
}
