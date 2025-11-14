package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.evenement.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {

    public static void ajouterCategorie(Categorie c, int evenementId) {
        String sql = "INSERT INTO categories_places (evenement_id,categorie,nb_places,prix) VALUES (?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, evenementId);
            ps.setString(2, c.getNom());
            ps.setInt(3, c.getNbPlaces());
            ps.setDouble(4, c.getPrix());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) c.setId(rs.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Categorie> getCategoriesParEvenement(int evenementId) {
        List<Categorie> liste = new ArrayList<>();
        String sql = "SELECT * FROM categories_places WHERE evenement_id=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, evenementId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorie c = new Categorie(rs.getString("categorie"),
                        rs.getInt("nb_places"),
                        rs.getDouble("prix"));
                c.setId(rs.getInt("id"));
                liste.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static void updateCategorie(Categorie c) {
        String sql = "UPDATE categories_places SET nb_places=?, prix=? WHERE id=?";
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
}
