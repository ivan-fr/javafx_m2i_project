package com.project.util;

import com.project.entity.utilisateur.Utilisateur;

/**
 * Singleton to manage the current user session.
 * Stores the logged-in user throughout the application.
 */
public class Session {
    private static Session instance;
    private Utilisateur utilisateur;

    /**
     * Private constructor to prevent direct instantiation.
     */
    private Session() {
    }

    /**
     * Returns the unique instance of Session.
     * 
     * @return The singleton instance.
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Sets the current user of the session.
     * 
     * @param u The user to store.
     */
    public void setUtilisateur(Utilisateur u) {
        utilisateur = u;
    }

    /**
     * Returns the current user.
     * 
     * @return The logged-in user, or null if none.
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Checks if a user is logged in.
     * 
     * @return true if logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return utilisateur != null;
    }

    /**
     * Logs out the current user and clears the session.
     */
    public void clear() {
        utilisateur = null;
    }
}