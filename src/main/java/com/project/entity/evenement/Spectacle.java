package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Spectacle extends Evenement {
    public Spectacle(String nom, LocalDateTime date, String lieu) {
        super(nom,date,lieu);
    }

}