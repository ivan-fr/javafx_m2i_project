package com.project.utilisateur;

import com.project.db.DbConnection;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOUtilisateur  {
    private Connection conn;

    public DAOUtilisateur() {
        this.conn = DbConnection.getConnection();
    }


    // Méthode qui retourne un ArrayList d'objets Utilisateur
    public ArrayList<Utilisateur> lister() {
        ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();
        try {
            String sql = "SELECT id, nom, email, mot_de_passe, type_compte, date_creation FROM utilisateur";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur;
                String type = rs.getString("type_compte");
                if (type.equalsIgnoreCase("CLIENT")) {
                    utilisateur = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                    );
                } else {
                    utilisateur = new Organisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                    );
                }
                utilisateur.setDateCreation(rs.getTimestamp("date_creation"));

                listeUtilisateurs.add(utilisateur);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listeUtilisateurs;
    }

    // Ajouter un utilisateur avec un objet Utilisateur
    public void ajouter(Utilisateur u) {
        try {
            String sql = "INSERT INTO utilisateur (nom, email, mot_de_passe, type_compte) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, u.getNom());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getMotDePasse());
            pstmt.setString(4, u.getTypeCompte());
            pstmt.executeUpdate();

            System.out.println("Utilisateur ajouté avec succès: " + u.getNom());
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Supprimer un utilisateur avec un objet Utilisateur
    public void supprimer(Utilisateur u) {
        try {
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, u.getId());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès: " + u.getNom());
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID: " + u.getId());
            }

            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mettre à jour un utilisateur
    public void modifier(Utilisateur u) {
        try {
            String sql = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ?, type_compte = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, u.getNom());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getMotDePasse());
            pstmt.setString(4, u.getTypeCompte());
            pstmt.setInt(5, u.getId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur modifié avec succès: " + u.getNom());
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID: " + u.getId());
            }

            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
