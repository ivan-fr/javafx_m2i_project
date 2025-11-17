package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.evenement.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAO {

    public static void ajouterEvenement(Evenement evenement) {
        String sql = "INSERT INTO evenements (nom,date,lieu,type_evenement,organisateur_id) VALUES (?,?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String type = evenement.getClass().getSimpleName().toUpperCase();

            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(evenement.getDate()));
            preparedStatement.setString(3, evenement.getLieu());
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, evenement.getOrganisateurId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int idEvenement = resultSet.getInt(1);
                evenement.setId(idEvenement);

                // Ajouter les catégories via CategoryPlaceDAO
                for (CategoryPlace categoryPlace : evenement.getCategories()) {
                    categoryPlace.setEvenementId(idEvenement);
                    CategoryPlaceDAO.ajouterCategoryPlace(categoryPlace);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static List<Evenement> listerEvenements() {
        List<Evenement> liste = new ArrayList<>();
        String sql = "SELECT * FROM evenements ORDER BY date";
        try (Connection conn = DbConnection.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                String lieu = resultSet.getString("lieu");
                String type = resultSet.getString("type_evenement");
                int idEvenement = resultSet.getInt("id");
                int organisateurId = resultSet.getInt("organisateur_id");

                Evenement evenement = switch (type) {
                    case "CONCERT" -> new Concert(nom, date, lieu);
                    case "SPECTACLE" -> new Spectacle(nom, date, lieu);
                    case "CONFERENCE" -> new Conference(nom, date, lieu);
                    default -> null;
                };

                if (evenement != null) {
                    evenement.setId(idEvenement);
                    evenement.setOrganisateurId(organisateurId);
                    evenement.getCategories().addAll(CategoryPlaceDAO.getCategoryPlacesParEvenement(evenement));
                    liste.add(evenement);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return liste;
    }
}
