package com.project.controller;

import com.project.dao.ReservationDAO;
import com.project.entity.Reservation;

import java.time.LocalDateTime;

public class ReservationController {

    public boolean reserve(int clientId,int eventId,int categoryId, int quantity) {
        ReservationDAO reservationDAO=new ReservationDAO();

        for(int i=0;i<quantity;i++){
            boolean result= reservationDAO.ajouterReservation(new Reservation(clientId, eventId, categoryId, LocalDateTime.now()));
            if(result==false){
                return false;
            }
        }
        return true;


    }
}
