package com.project.entity.evenement;

import java.sql.Timestamp;

public class Spectacle extends Evenement {
	 public Spectacle(int id, String nom, Timestamp dateEvenement, String lieu, int organisisateurId) {
	        super(id, nom, dateEvenement, lieu, organisisateurId);
	    }

	    public Spectacle(String nom, Timestamp dateEvenement, String lieu, int organisisateurId) {
	        super(0, nom, dateEvenement, lieu, organisisateurId);
	    }

}