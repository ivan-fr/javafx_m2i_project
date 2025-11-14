package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.evenement.CategoryPlace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPlaceDAO {

    public static void ajouterCategoryPlace(CategoryPlace c) {
        String sql = "INSERT INTO category_places (evenement_id,categorie,nb_places,prix) VALUES (?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getEvenementId());
            ps.setString(2, c.getCategorie());
            ps.setInt(3, c.getNbPlaces());
            ps.setDouble(4, c.getPrix());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<CategoryPlace> getCategoryPlacesParEvenement(int evenementId) {
        List<CategoryPlace> liste = new ArrayList<>();
        String sql = "SELECT * FROM category_places WHERE evenement_id=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, evenementId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryPlace c = new CategoryPlace(rs.getString("categorie"),
                        rs.getInt("nb_places"),
                        rs.getDouble("prix"));
                c.setId(rs.getInt("id"));
                c.setEvenementId(evenementId);
                liste.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static void updateCategoryPlace(CategoryPlace c) {
        String sql = "UPDATE category_places SET nb_places=?, prix=? WHERE id=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getNbPlaces());
            ps.setDouble(2, c.getPrix());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calcule le nombre de places disponibles pour une category_place
     * Formule : nb_places - nombre de réservations
     */
    public static int getPlacesDisponibles(CategoryPlace c) {
        return getPlacesDisponiblesById(c.getId());
    }

    /**
     * Surcharge pour obtenir les places disponibles par ID
     * Utile pour les vérifications avant d'avoir l'objet complet
     */
    public static int getPlacesDisponiblesById(int categoryPlaceId) {
        String sql = "SELECT cp.nb_places - COUNT(r.id) AS places_disponibles " +
                     "FROM category_places cp " +
                     "LEFT JOIN reservations r ON cp.id = r.category_place_id " +
                     "WHERE cp.id = ? " +
                     "GROUP BY cp.id, cp.nb_places";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryPlaceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("places_disponibles");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
