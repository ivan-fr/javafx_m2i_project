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

public class UtilisateurDAO {
    private Connection conn;

    public UtilisateurDAO() {
        this.conn = DbConnection.getConnection();
    }


    // Méthode qui retourne un ArrayList d'objets Utilisateur
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
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe")
                    );
                } else {
                    utilisateur = new Organisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe")
                    );
                }
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

    // Ajouter un utilisateur avec un objet Utilisateur
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

    public static Utilisateur login(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateurs WHERE email=? AND mot_de_passe=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String type = resultSet.getString("type_compte");
                if (type.equals("CLIENT")) return new Client(nom, email, motDePasse);
                else return new Organisateur(nom, email, motDePasse);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    // Supprimer un utilisateur avec un objet Utilisateur
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

    // Mettre à jour un utilisateur
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
