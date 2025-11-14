package com.projet.evenement;

import java.sql.Timestamp;

public class Conference extends Evenement {

    public Conference(int id, String nom, Timestamp date, String lieu,
                      int places, double prix) {
        super(id, nom, date, lieu, places, prix, "CONFERENCE");
    }

    public Conference(String nom, Timestamp date, String lieu,
                      int places, double prix) {
        super(nom, date, lieu, places, prix, "CONFERENCE");
    }
}

