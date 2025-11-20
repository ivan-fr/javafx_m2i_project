package com.project.entity.evenement;

import java.time.LocalDateTime;

/**
 * Represents a conference event.
 */
public class Conference extends Evenement {
    /**
     * Constructor for a conference.
     * 
     * @param nom            Name of the conference.
     * @param dateEvenement  Date and time.
     * @param lieu           Location.
     * @param organisateurId Organizer ID.
     */
    public Conference(String nom, LocalDateTime dateEvenement, String lieu, int organisateurId) {
        super(nom, dateEvenement, lieu, organisateurId);
    }
}