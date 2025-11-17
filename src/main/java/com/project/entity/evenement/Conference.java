package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Conference extends Evenement {

	public Conference(int id, String nom, LocalDateTime dateEvenement, String lieu, int organisisateurId) {
        super(id, nom, dateEvenement, lieu, organisisateurId);
    }

    public Conference(String nom, LocalDateTime dateEvenement, String lieu, int organisisateurId) {
        super(0, nom, dateEvenement, lieu, organisisateurId);
    }

}