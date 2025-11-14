package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.Reservation;

import java.sql.*;
import java.time.LocalDateTime;

public class ReservationDAO {

    /**
     * Vérifie si un client a déjà réservé pour un événement
     * Règle : un client = 1 réservation max par événement
     */
    public static boolean clientADejaReserve(Reservation r) {
        String sql = "SELECT COUNT(*) AS nb FROM reservations WHERE client_id = ? AND evenement_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getClientId());
            ps.setInt(2, r.getEvenementId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("nb") > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Ajoute une réservation avec vérifications :
     * 1. Le client n'a pas déjà réservé pour cet événement
     * 2. Il reste des places disponibles pour cette category_place
     *
     * @param r La réservation à ajouter (doit contenir clientId, evenementId, categoryPlaceId)
     * @return true si la réservation a été ajoutée, false sinon
     */
    public static boolean ajouterReservation(Reservation r) {
        // Définir la date de réservation si elle n'est pas déjà définie
        if (r.getDateReservation() == null) {
            r.setDateReservation(LocalDateTime.now());
        }

        // Vérification 1 : Le client a-t-il déjà réservé pour cet événement ?
        if (clientADejaReserve(r)) {
            System.out.println("Erreur : Ce client a déjà réservé pour cet événement");
            return false;
        }

        // Vérification 2 : Y a-t-il encore des places disponibles ?
        int placesDisponibles = CategoryPlaceDAO.getPlacesDisponiblesById(r.getCategoryPlaceId());
        if (placesDisponibles <= 0) {
            System.out.println("Erreur : Plus de places disponibles pour cette catégorie");
            return false;
        }

        // Insertion de la réservation
        String sql = "INSERT INTO reservations (client_id,evenement_id,category_place_id,date_reservation) VALUES (?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getClientId());
            ps.setInt(2, r.getEvenementId());
            ps.setInt(3, r.getCategoryPlaceId());
            ps.setTimestamp(4, Timestamp.valueOf(r.getDateReservation()));
            ps.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                r.setId(rs.getInt(1));
            }

            System.out.println("Réservation effectuée avec succès !");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
