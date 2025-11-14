package com.project;

import com.project.db.DbConnection;
import com.project.utilisateur.DAOUtilisateur;
import com.project.utilisateur.Utilisateur;
import com.project.utilisateur.Client;
import com.project.utilisateur.Organisateur;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DAOUtilisateur dao = new DAOUtilisateur();

        Utilisateur u1 = new Utilisateur("Jean Dupont", "jean@email.com", "pass123", "CLIENT");
        Utilisateur u2 = new Utilisateur("Marie Martin", "marie@email.com", "pass456", "ORGANISATEUR");

        dao.ajouter(u1);
        dao.ajouter(u2);

        ArrayList<Utilisateur> users = dao.lister();
        
        
        Client c = new Client("Alice", "alice@mail.com", "1234");
        Organisateur o = new Organisateur("Bob", "bob@mail.com", "abcd");

       
        
        ArrayList<Utilisateur> liste = dao.lister();
        for (Utilisateur u : liste) {
            System.out.println(u.getNom() + " -> " + u.getTypeCompte());
        }

        dao.supprimer(liste.get(0));
        dao.supprimer(liste.get(1));
        
        DbConnection.fermerConnexion();
    }
}
