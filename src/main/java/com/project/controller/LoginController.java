package com.project.controller;

import com.project.dao.UtilisateurDAO;
import com.project.entity.utilisateur.Utilisateur;

public class LoginController {

    /**
     * Authentifie un utilisateur avec son email et mot de passe
     *
     * @param email L'email de l'utilisateur
     * @param password Le mot de passe de l'utilisateur
     * @return L'utilisateur authentifié, ou null si l'authentification échoue
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
