package com.project.entity.evenement;

import java.time.LocalDateTime;

public class Concert extends Evenement {
    public Concert(String nom, LocalDateTime date, String lieu) {
        super(nom,date,lieu);
    }


}