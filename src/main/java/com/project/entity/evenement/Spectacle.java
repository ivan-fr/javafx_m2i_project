package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Spectacle extends Evenement {
    public Spectacle(String nom, LocalDateTime dateEvenement, String lieu, int organisateurId) {
        super(nom, dateEvenement, lieu, organisateurId);
    }
}