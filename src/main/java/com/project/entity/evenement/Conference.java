package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Conference extends Evenement {
    public Conference(String nom, LocalDateTime dateEvenement, String lieu, int organisateurId) {
        super(nom, dateEvenement, lieu, organisateurId);
    }
}