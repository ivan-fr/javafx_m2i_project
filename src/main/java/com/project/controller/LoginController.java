package com.project.controller;

import com.project.dao.UtilisateurDAO;
import com.project.entity.utilisateur.Utilisateur;

/**
 * Controller for user authentication.
 */
public class LoginController {

    /**
     * Authenticates a user.
     * 
     * @param email    The user's email.
     * @param password The user's password.
     * @return The authenticated user, or null if failed.
     */
    public Utilisateur login(String email, String password) {
        // Validation des entrées
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        // Appel au DAO pour l'authentification
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        return utilisateurDAO.login(email, password);
    }
}
