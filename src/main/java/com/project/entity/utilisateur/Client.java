package com.project.entity.utilisateur;

public class Client extends Utilisateur {

    public Client() {
        super();
        this.setTypeCompte("CLIENT");
    }

    public Client(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse, "CLIENT");
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", typeCompte='" + getTypeCompte() + '\'' +
                '}';
    }
}
