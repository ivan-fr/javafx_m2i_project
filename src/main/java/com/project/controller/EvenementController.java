package com.project.controller;

import com.project.dao.EvenementDAO;
import com.project.entity.evenement.Evenement;

import java.util.List;

public class EvenementController {

    /**
     * Récupère la liste de tous les événements
     *
     * @return Liste des événements
     */
    public List<Evenement> getAllEvenements() {
        EvenementDAO evenementDAO = new EvenementDAO();
        return evenementDAO.listerEvenements();
    }

    /**
     * Récupère un événement par son ID
     *
     * @param id L'identifiant de l'événement
     * @return L'événement trouvé, ou null si non trouvé
     */
    public Evenement getEvenementById(int id) {
        EvenementDAO evenementDAO = new EvenementDAO();
        return evenementDAO.getEvenementById(id);
    }

    /**
     * Modifie un événement existant
     *
     * @param evenement L'événement à modifier
     */
    public void updateEvenement(Evenement evenement) {
        EvenementDAO evenementDAO = new EvenementDAO();
        evenementDAO.modifierEvenement(evenement);
    }

    /**
     * Crée un nouvel événement
     *
     * @param evenement L'événement à créer
     * @return true si la création a réussi, false sinon
     */
    public boolean createEvenement(Evenement evenement) {
        // Validation
        if (evenement == null) {
            return false;
        }

        if (evenement.getNom() == null || evenement.getNom().trim().isEmpty()) {
            return false;
        }

        if (evenement.getLieu() == null || evenement.getLieu().trim().isEmpty()) {
            return false;
        }

        if (evenement.getDate() == null) {
            return false;
        }

        if (evenement.getCategories() == null || evenement.getCategories().isEmpty()) {
            return false;
        }

        // Sauvegarde via DAO
        try {
            EvenementDAO evenementDAO = new EvenementDAO();
            evenementDAO.ajouterEvenement(evenement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un événement
     *
     * @param id L'identifiant de l'événement à supprimer
     */
    public void deleteEvenement(int id) {
        EvenementDAO evenementDAO = new EvenementDAO();
        evenementDAO.supprimerEvenement(id);
    }
}
