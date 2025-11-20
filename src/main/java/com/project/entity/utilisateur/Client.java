package com.project.entity.utilisateur;

/**
 * Represents a client user.
 * Clients can book tickets for events.
 */
public class Client extends Utilisateur {

    /**
     * Default constructor.
     * Sets account type to 'CLIENT'.
     */
    public Client() {
        super();
        this.setTypeCompte("CLIENT");
    }

    /**
     * Constructor with details.
     * 
     * @param nom        Name of the client.
     * @param email      Email address.
     * @param motDePasse Password.
     */
    public Client(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse, "CLIENT");
    }

    /**
     * Returns a string representation of the client.
     * 
     * @return String with client details.
     */
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
