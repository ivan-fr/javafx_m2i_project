package com.project.controller;

import com.project.dao.EvenementDAO;
import com.project.entity.evenement.Evenement;

import java.util.List;

/**
 * Controller for handling event-related operations.
 */
public class EvenementController {

    /**
     * Retrieves all events.
     * 
     * @return A list of all events.
     */
    public List<Evenement> getAllEvenements() {
        EvenementDAO evenementDAO = new EvenementDAO();
        return evenementDAO.listerEvenements();
    }

    /**
     * Finds an event by its ID.
     * 
     * @param id The event ID.
     * @return The event if found, null otherwise.
     */
    public Evenement getEvenementById(int id) {
        EvenementDAO evenementDAO = new EvenementDAO();
        return evenementDAO.getEvenementById(id);
    }

    /**
     * Updates an existing event.
     * 
     * @param evenement The event with updated details.
     */
    public void updateEvenement(Evenement evenement) {
        EvenementDAO evenementDAO = new EvenementDAO();
        evenementDAO.modifierEvenement(evenement);
    }

    /**
     * Creates a new event.
     * Validates the event data before saving.
     * 
     * @param evenement The event to create.
     * @return true if created successfully, false if invalid.
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
     * Deletes an event.
     * 
     * @param id The ID of the event to delete.
     */
    public void deleteEvenement(int id) {
        EvenementDAO evenementDAO = new EvenementDAO();
        evenementDAO.supprimerEvenement(id);
    }
}
