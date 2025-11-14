package com.project;

import com.project.utilisateur.DAOUtilisateur;
import com.project.utilisateur.Utilisateur;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DAOUtilisateur dao = new DAOUtilisateur();

        Utilisateur u1 = new Utilisateur("Jean Dupont", "jean@email.com", "pass123", "CLIENT");
        Utilisateur u2 = new Utilisateur("Marie Martin", "marie@email.com", "pass456", "ORGANISATEUR");

        dao.ajouter(u1);
        dao.ajouter(u2);

        ArrayList<Utilisateur> users = dao.lister();
        for (Utilisateur u : users) {
            System.out.println(u);
            dao.supprimer(u);
        }

        dao.fermerConnexion();
    }
}
