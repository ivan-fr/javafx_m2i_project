package com.project.entity.evenement;

import java.time.LocalDateTime;

/**
 * Represents a show or spectacle.
 */
public class Spectacle extends Evenement {
    /**
     * Constructor for a spectacle.
     * 
     * @param nom            Name of the show.
     * @param dateEvenement  Date and time.
     * @param lieu           Location.
     * @param organisateurId Organizer ID.
     */
    public Spectacle(String nom, LocalDateTime dateEvenement, String lieu, int organisateurId) {
        super(nom, dateEvenement, lieu, organisateurId);
    }
}