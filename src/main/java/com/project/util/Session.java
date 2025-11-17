package com.project.util;

import com.project.entity.utilisateur.Utilisateur;

public class Session {
    private static Utilisateur utilisateur;

    // Empêche l'instanciation
    private Session() {}

    public static void setUtilisateur(Utilisateur u) {
        utilisateur = u;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void clear() {
        utilisateur = null;
    }
}