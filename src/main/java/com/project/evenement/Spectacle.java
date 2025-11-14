package com.project.evenement;

import java.sql.Timestamp;
public class Spectacle extends Evenement {

    public Spectacle(int id, String nom, Timestamp date, String lieu,
                     int places, double prix) {
        super(id, nom, date, lieu, places, prix, "SPECTACLE");
    }

    public Spectacle(String nom, Timestamp date, String lieu,
                     int places, double prix) {
        super(nom, date, lieu, places, prix, "SPECTACLE");
    }
}
