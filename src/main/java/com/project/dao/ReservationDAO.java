package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.Reservation;


import java.sql.*;
import java.time.LocalDateTime;

public class ReservationDAO {
    private Connection conn;

    public ReservationDAO() {
        this.conn = DbConnection.getConnection();
    }
    public static void ajouterReservation(Reservation r, int clientId, int evenementId) {
        String sql = "INSERT INTO reservations (client_id,evenement_id,categorie,date_reservation,montant_total) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ps.setInt(2, evenementId);
            ps.setString(3, r.getCategorie());
            ps.setInt(4, r.getNbTickets());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
