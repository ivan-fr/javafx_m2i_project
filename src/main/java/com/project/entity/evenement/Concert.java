package com.project.entity.evenement;

import java.time.LocalDateTime;

/**
 * Represents a concert event.
 */
public class Concert extends Evenement {
    /**
     * Constructor for a concert.
     * 
     * @param nom            Name of the concert.
     * @param dateEvenement  Date and time.
     * @param lieu           Location.
     * @param organisateurId Organizer ID.
     */
    public Concert(String nom, LocalDateTime dateEvenement, String lieu, int organisateurId) {
        super(nom, dateEvenement, lieu, organisateurId);
    }
}