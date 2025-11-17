package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Concert extends Evenement {
    public Concert(String nom, LocalDateTime dateEvenement, String lieu, int organisateurId) {
        super(nom, dateEvenement, lieu, organisateurId);
    }
}