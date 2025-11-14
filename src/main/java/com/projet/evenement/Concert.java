package com.projet.evenement;

import java.sql.Timestamp;

public class Concert extends Evenement {

    public Concert(int id, String nom, Timestamp date, String lieu,
                   int places, double prix) {
        super(id, nom, date, lieu, places, prix, "CONCERT");
    }

    public Concert(String nom, Timestamp date, String lieu,
                   int places, double prix) {
        super(nom, date, lieu, places, prix, "CONCERT");
    }
}
