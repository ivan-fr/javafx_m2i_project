package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.evenement.CategoryPlace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPlaceDAO {

	public void ajouterCategoryPlace(CategoryPlace categoryPlace) {
	    String sql = "INSERT INTO category_places (evenement_id, categorie, nb_places, prix) VALUES (?,?,?,?)";
	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        preparedStatement.setInt(1, categoryPlace.getEvenementId());
	        preparedStatement.setString(2, categoryPlace.getCategorie());
	        preparedStatement.setInt(3, categoryPlace.getNbPlaces());
	        preparedStatement.setDouble(4, categoryPlace.getPrix());
	        preparedStatement.executeUpdate();

	        ResultSet resultSet = preparedStatement.getGeneratedKeys();
	        if (resultSet.next()) {
	            categoryPlace.setId(resultSet.getInt(1));
	        }

	    } catch (SQLException exception) {
	        exception.printStackTrace();
	    }
	}


    public List<CategoryPlace> getCategoryPlacesParEvenement(com.project.entity.evenement.Evenement evenement) {
        List<CategoryPlace> liste = new ArrayList<>();
        String sql = "SELECT * FROM category_places WHERE evenement_id=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, evenement.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CategoryPlace categoryPlace = new CategoryPlace(resultSet.getString("categorie"),
                        resultSet.getInt("nb_places"),
                        resultSet.getDouble("prix"));
                categoryPlace.setId(resultSet.getInt("id"));
                categoryPlace.setEvenementId(evenement.getId());
                liste.add(categoryPlace);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return liste;
    }

    public void updateCategoryPlace(CategoryPlace categoryPlace) {
        String sql = "UPDATE category_places SET nb_places=?, prix=? WHERE id=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryPlace.getNbPlaces());
            preparedStatement.setDouble(2, categoryPlace.getPrix());
            preparedStatement.setInt(3, categoryPlace.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Calcule le nombre de places disponibles pour une category_place
     * Formule : nb_places - nombre de réservations
     */
    public int getPlacesDisponibles(CategoryPlace categoryPlace) {
        String sql = "SELECT cp.nb_places - COUNT(r.id) AS places_disponibles " +
                     "FROM category_places cp " +
                     "LEFT JOIN reservations r ON cp.id = r.category_place_id " +
                     "WHERE cp.id = ? " +
                     "GROUP BY cp.id, cp.nb_places";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryPlace.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("places_disponibles");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    
    public void supprimerCategoryPlacesParEvenement(int evenementId) {
        try {
            String sql = "DELETE FROM category_places WHERE evenement_id = ?";
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, evenementId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
