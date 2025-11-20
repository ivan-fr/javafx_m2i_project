package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.Reservation;
import com.project.entity.evenement.CategoryPlace;
import com.project.exception.AnnulationTardiveException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing reservations in the database.
 */
public class ReservationDAO {

    /**
     * Adds a new reservation.
     * Checks if there are enough places available before adding.
     * 
     * @param reservation The reservation to add.
     * @return true if successful, false otherwise.
     */
    public boolean ajouterReservation(Reservation reservation) {
        // Définir la date de réservation si elle n'est pas déjà définie
        if (reservation.getDateReservation() == null) {
            reservation.setDateReservation(LocalDateTime.now());
        }

        // Créer un objet CategoryPlace temporaire avec l'ID pour la vérification
        CategoryPlace categoryPlace = new CategoryPlace("", 0, 0.0);
        categoryPlace.setId(reservation.getCategoryPlaceId());
        CategoryPlaceDAO categoryPlaceDAO = new CategoryPlaceDAO();
        int placesDisponibles = categoryPlaceDAO.getPlacesDisponibles(categoryPlace.getId());
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

    /**
     * Retrieves all reservations for a specific client.
     * 
     * @param clientId The client ID.
     * @return A list of Reservation objects.
     */
    public static List<Reservation> getReservationsByClientId(int clientId) {
        List<Reservation> list = new ArrayList<>();

        String sql = """
                    SELECT r.id AS res_id,
                           r.date_reservation,
                           e.id AS evt_id,
                           e.nom AS evt_nom,
                           e.date AS evt_date,
                           cp.id AS cp_id,
                           cp.categorie,
                           cp.prix
                    FROM reservations r
                    JOIN evenements e ON r.evenement_id = e.id
                    JOIN category_places cp ON r.category_place_id = cp.id
                    WHERE r.client_id = ?
                    ORDER BY r.date_reservation DESC
                """;

        try (Connection conn = DbConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, clientId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();

                r.setId(rs.getInt("res_id"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());

                r.setEvenementId(rs.getInt("evt_id"));
                r.setEvenementNom(rs.getString("evt_nom"));
                r.setEvenementDate(rs.getTimestamp("evt_date").toLocalDateTime());

                r.setCategoryPlaceId(rs.getInt("cp_id"));
                r.setCategorie(rs.getString("categorie"));
                r.setPrixUnitaire(rs.getDouble("prix"));

                r.setNombrePlaces(1); // si tu veux plus tard gérer plusieurs places : changer ici !
                r.setPrixTotal(r.getPrixUnitaire() * r.getNombrePlaces());

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Cancels a reservation.
     * Checks if the cancellation is made at least 24 hours before the event.
     * 
     * @param reservationId The reservation ID.
     * @param clientId      The client ID (for verification).
     * @throws AnnulationTardiveException If cancelled too late.
     */
    public void annulerReservation(int reservationId, int clientId)
            throws AnnulationTardiveException {

        String sqlSelect = """
                    SELECT r.id,
                           r.client_id,
                           r.evenement_id,
                           e.date AS evt_date
                    FROM reservations r
                    JOIN evenements e ON r.evenement_id = e.id
                    WHERE r.id = ?
                """;

        try (Connection conn = DbConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(sqlSelect)) {

            st.setInt(1, reservationId);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new IllegalArgumentException("Réservation introuvable.");
            }

            int clientIdDb = rs.getInt("client_id");
            if (clientIdDb != clientId) {
                throw new IllegalArgumentException("Vous ne pouvez pas annuler cette réservation.");
            }

            LocalDateTime eventDate = rs.getTimestamp("evt_date").toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            // Règle des 24h
            if (now.isAfter(eventDate.minusHours(24))) {
                throw new AnnulationTardiveException(
                        "Impossible d'annuler moins de 24 heures avant l'événement.");
            }

            // Si tout est OK → suppression de la réservation
            String sqlDelete = "DELETE FROM reservations WHERE id = ?";
            try (PreparedStatement deleteSt = conn.prepareStatement(sqlDelete)) {
                deleteSt.setInt(1, reservationId);
                deleteSt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'annulation de la réservation.", e);
        }
    }
}
