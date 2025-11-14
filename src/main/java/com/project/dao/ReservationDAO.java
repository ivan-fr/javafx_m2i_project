package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.Reservation;
import com.project.entity.evenement.CategoryPlace;

import java.sql.*;
import java.time.LocalDateTime;

public class ReservationDAO {

    /**
     * Vérifie si un client a déjà réservé pour un événement
     * Règle : un client = 1 réservation max par événement
     */
    public static boolean clientADejaReserve(Reservation reservation) {
        String sql = "SELECT COUNT(*) AS nb FROM reservations WHERE client_id = ? AND evenement_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getClientId());
            preparedStatement.setInt(2, reservation.getEvenementId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("nb") > 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Ajoute une réservation avec vérifications :
     * 1. Le client n'a pas déjà réservé pour cet événement
     * 2. Il reste des places disponibles pour cette category_place
     *
     * @param reservation La réservation à ajouter (doit contenir clientId, evenementId, categoryPlaceId)
     * @return true si la réservation a été ajoutée, false sinon
     */
    public static boolean ajouterReservation(Reservation reservation) {
        // Définir la date de réservation si elle n'est pas déjà définie
        if (reservation.getDateReservation() == null) {
            reservation.setDateReservation(LocalDateTime.now());
        }

        // Vérification 1 : Le client a-t-il déjà réservé pour cet événement ?
        if (clientADejaReserve(reservation)) {
            System.out.println("Erreur : Ce client a déjà réservé pour cet événement");
            return false;
        }

        // Vérification 2 : Y a-t-il encore des places disponibles ?
        // Créer un objet CategoryPlace temporaire avec l'ID pour la vérification
        CategoryPlace categoryPlace = new CategoryPlace("", 0, 0.0);
        categoryPlace.setId(reservation.getCategoryPlaceId());
        int placesDisponibles = CategoryPlaceDAO.getPlacesDisponibles(categoryPlace);
        if (placesDisponibles <= 0) {
            System.out.println("Erreur : Plus de places disponibles pour cette catégorie");
            return false;
        }

        // Insertion de la réservation
        String sql = "INSERT INTO reservations (client_id,evenement_id,category_place_id,date_reservation) VALUES (?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, reservation.getClientId());
            preparedStatement.setInt(2, reservation.getEvenementId());
            preparedStatement.setInt(3, reservation.getCategoryPlaceId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(reservation.getDateReservation()));
            preparedStatement.executeUpdate();

            // Récupérer l'ID généré
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                reservation.setId(resultSet.getInt(1));
            }

            System.out.println("Réservation effectuée avec succès !");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
