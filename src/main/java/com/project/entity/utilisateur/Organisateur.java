package com.project.entity.utilisateur;

/**
 * Represents an organizer user.
 * Organizers can create and manage events.
 */
public class Organisateur extends Utilisateur {

    /**
     * Default constructor.
     * Sets account type to 'ORGANISATEUR'.
     */
    public Organisateur() {
        super();
        this.setTypeCompte("ORGANISATEUR");
    }

    /**
     * Constructor with details.
     * 
     * @param nom        Name of the organizer.
     * @param email      Email address.
     * @param motDePasse Password.
     */
    public Organisateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse, "ORGANISATEUR");
    }

    /**
     * Returns a string representation of the organizer.
     * 
     * @return String with organizer details.
     */
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
