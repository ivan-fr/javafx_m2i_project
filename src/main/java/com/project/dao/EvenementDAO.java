package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.evenement.*;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

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
    
    public void modifierEvenement(Evenement evt) {
        try {
            String sql = "UPDATE evenements SET nom=?, date=?, lieu=?, organisateur_id=? WHERE id=?";
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, evt.getNom());
            pstmt.setTimestamp(2, Timestamp.valueOf(evt.getDate()));
            pstmt.setString(3, evt.getLieu());
            pstmt.setInt(4, evt.getOrganisateurId());
            pstmt.setInt(5, evt.getId());

            pstmt.executeUpdate();
            pstmt.close();

            // Mise à jour des catégories
            CategoryPlaceDAO DAO = new CategoryPlaceDAO();
            DAO.supprimerCategoryPlacesParEvenement(evt.getId());
            for (CategoryPlace cp : evt.getCategories()) {
                DAO.ajouterCategoryPlace(cp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void supprimerEvenement(int id) {
        try {
            CategoryPlaceDAO cpDAO = new CategoryPlaceDAO();
            cpDAO.supprimerCategoryPlacesParEvenement(id);

            String sql = "DELETE FROM evenements WHERE id=?";
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Evenement getEvenementById(int id) {
        try {
            String sql = "SELECT * FROM evenements WHERE id=?";
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("type"); // Vient de la BDD

                Evenement evt = creerEvenementDepuisResultSet(rs, type);

                // Charger catégories
                evt.setCategories(CategoryPlaceDAO.getCategoryPlacesParEvenement(evt));

                return evt;
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Evenement creerEvenementDepuisResultSet(ResultSet rs, String type) throws Exception {

        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        Timestamp timestamp = rs.getTimestamp("date");
        String lieu = rs.getString("lieu");
        int organisateurId = rs.getInt("organisateur_id");

        switch (type.toUpperCase()) {
            case "CONCERT":
                return new Concert(id, nom, timestamp.toLocalDateTime(), lieu, organisateurId);

            case "SPECTACLE":
                return new Spectacle(id, nom, timestamp.toLocalDateTime(), lieu, organisateurId);

            case "CONFERENCE":
                return new Conference(id, nom, timestamp.toLocalDateTime(), lieu, organisateurId);

            default:
                throw new Exception("Type d'événement inconnu : " + type);
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
                Timestamp timestamp = resultSet.getTimestamp("date");
                String lieu = resultSet.getString("lieu");
                String type = resultSet.getString("type_evenement");
                int idEvenement = resultSet.getInt("id");
                int organisateurId = resultSet.getInt("organisateur_id");


                Evenement evenement = switch (type.toUpperCase()) {
                case "CONCERT" -> new Concert(nom, timestamp.toLocalDateTime(), lieu, organisateurId);
                case "SPECTACLE" -> new Spectacle(nom, timestamp.toLocalDateTime(), lieu, organisateurId);
                case "CONFERENCE" -> new Conference(nom, timestamp.toLocalDateTime(), lieu, organisateurId);
                default -> throw new IllegalArgumentException("Type inconnu : " + type);
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
