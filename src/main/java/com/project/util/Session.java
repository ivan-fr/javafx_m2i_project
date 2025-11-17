package com.project.util;

import com.project.entity.utilisateur.Utilisateur;

/**
 * Singleton pour gérer la session utilisateur courante
 * Stocke l'utilisateur connecté à travers toute l'application
 */
public class Session {
    private static Session instance;
    private Utilisateur utilisateur;

    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private Session() {}

    /**
     * Récupère l'instance unique de Session (Singleton pattern)
     *
     * @return L'instance unique de Session
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Définit l'utilisateur courant de la session
     *
     * @param u L'utilisateur à stocker dans la session
     */
    public void setUtilisateur(Utilisateur u) {
        utilisateur = u;
    }

    /**
     * Récupère l'utilisateur courant de la session
     *
     * @return L'utilisateur connecté, ou null si aucun utilisateur n'est connecté
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Vérifie si un utilisateur est connecté
     *
     * @return true si un utilisateur est connecté, false sinon
     */
    public boolean isLoggedIn() {
        return utilisateur != null;
    }

    /**
     * Déconnecte l'utilisateur courant et nettoie la session
     */
    public void clear() {
        utilisateur = null;
    }
}