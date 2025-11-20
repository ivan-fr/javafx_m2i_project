package com.project.dao;

import com.project.util.DbConnection;
import com.project.entity.utilisateur.Client;
import com.project.entity.utilisateur.Organisateur;
import com.project.entity.utilisateur.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO for managing users in the database.
 * Handles login, registration, and CRUD operations.
 */
public class UtilisateurDAO {
    private Connection conn;

    /**
     * Constructor.
     * Initializes the database connection.
     */
    public UtilisateurDAO() {
        this.conn = DbConnection.getConnection();
    }

    /**
     * Lists all users in the system.
     * 
     * @return A list of Utilisateur objects.
     */
    public ArrayList<Utilisateur> lister() {
        ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();
        try {
            String sql = "SELECT id, nom, email, mot_de_passe, type_compte, date_creation FROM utilisateurs";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Utilisateur utilisateur;
                String type = resultSet.getString("type_compte");
                if (type.equalsIgnoreCase("CLIENT")) {
                    utilisateur = new Client(
                            resultSet.getString("nom"),
                            resultSet.getString("email"),
                            resultSet.getString("mot_de_passe"));
                } else {
                    utilisateur = new Organisateur(
                            resultSet.getString("nom"),
                            resultSet.getString("email"),
                            resultSet.getString("mot_de_passe"));
                }
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setDateCreation(resultSet.getTimestamp("date_creation"));

                listeUtilisateurs.add(utilisateur);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listeUtilisateurs;
    }

    /**
     * Adds a new user to the database.
     * 
     * @param utilisateur The user to add.
     */
    public void ajouter(Utilisateur utilisateur) {
        try {
            String sql = "INSERT INTO utilisateurs (nom, email, mot_de_passe, type_compte) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getMotDePasse());
            preparedStatement.setString(4, utilisateur.getTypeCompte());
            preparedStatement.executeUpdate();

            System.out.println("Utilisateur ajouté avec succès: " + utilisateur.getNom());
            preparedStatement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Authenticates a user.
     * 
     * @param email      The email address.
     * @param motDePasse The password.
     * @return The Utilisateur object if successful, null otherwise.
     */
    public Utilisateur login(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateurs WHERE email=? AND mot_de_passe=?";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type_compte");

                Utilisateur user;
                if (type.equals("ORGANISATEUR")) {
                    user = new Organisateur();
                } else {
                    user = new Client();
                }

                user.setId(resultSet.getInt("id")); // ⛔ tu as oublié ça !
                user.setNom(resultSet.getString("nom"));
                user.setEmail(resultSet.getString("email"));
                user.setMotDePasse(resultSet.getString("mot_de_passe"));
                user.setTypeCompte(type);

                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a user from the database.
     * 
     * @param utilisateur The user to delete.
     */
    public void supprimer(Utilisateur utilisateur) {
        try {
            String sql = "DELETE FROM utilisateurs WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, utilisateur.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès: " + utilisateur.getNom());
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID: " + utilisateur.getId());
            }

            preparedStatement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Updates a user's information.
     * 
     * @param utilisateur The user with updated details.
     */
    public void modifier(Utilisateur utilisateur) {
        try {
            String sql = "UPDATE utilisateurs SET nom = ?, email = ?, mot_de_passe = ?, type_compte = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getMotDePasse());
            preparedStatement.setString(4, utilisateur.getTypeCompte());
            preparedStatement.setInt(5, utilisateur.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur modifié avec succès: " + utilisateur.getNom());
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID: " + utilisateur.getId());
            }

            preparedStatement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
