package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Spectacle extends Evenement {
	 public Spectacle(int id, String nom, LocalDateTime dateEvenement, String lieu, int organisisateurId) {
	        super(id, nom, dateEvenement, lieu, organisisateurId);
	    }

	    public Spectacle(String nom, LocalDateTime dateEvenement, String lieu, int organisisateurId) {
	        super(0, nom, dateEvenement, lieu, organisisateurId);
	    }

}