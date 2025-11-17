package com.project.entity.evenement;

import java.sql.Timestamp;

public class Concert extends Evenement {
	public Concert(int id, String nom, Timestamp dateEvenement, String lieu, int organisisateurId) {
        super(id, nom, dateEvenement, lieu, organisisateurId);
    }

    public Concert(String nom, Timestamp dateEvenement, String lieu, int organisisateurId) {
        super(0, nom, dateEvenement, lieu, organisisateurId); // id auto géré par la BD
    }

}