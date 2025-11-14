package com.project.dao;



import com.project.util.DbConnection;
import com.project.entity.evenement.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAO {

    public static void ajouterEvenement(Evenement e, String type) {
        String sql = "INSERT INTO evenements (nom,date,lieu,type_evenement) VALUES (?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNom());
            ps.setTimestamp(2, Timestamp.valueOf(e.getDate()));
            ps.setString(3, e.getLieu());
            ps.setString(4, type);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idEvenement = rs.getInt(1);
                e.setId(idEvenement);
                // Ajouter les catégories via CategorieDAO
                for (Categorie c : e.getCategories()) {
                    CategorieDAO.ajouterCategorie(c, idEvenement);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<Evenement> listerEvenements() {
        List<Evenement> liste = new ArrayList<>();
        String sql = "SELECT * FROM evenements";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                String lieu = rs.getString("lieu");
                String type = rs.getString("type_evenement");
                int idEvenement = rs.getInt("id");

                Evenement e = switch (type) {
                    case "CONCERT" -> new Concert(nom, date, lieu);
                    case "SPECTACLE" -> new Spectacle(nom, date, lieu);
                    case "CONFERENCE" -> new Conference(nom, date, lieu);
                    default -> null;
                };

                if (e != null) {
                    e.setId(idEvenement);
                    e.getCategories().addAll(CategorieDAO.getCategoriesParEvenement(idEvenement));
                    liste.add(e);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }
}
