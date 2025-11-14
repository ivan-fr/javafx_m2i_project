package com.project.entity.evenement;

import com.project.entity.evenement.Evenement;

import java.time.LocalDateTime;

public class Spectacle extends Evenement {
    public Spectacle(String nom, LocalDateTime date, String lieu) {
        super(nom,date,lieu);
    }

}