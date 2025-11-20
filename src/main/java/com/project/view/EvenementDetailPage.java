package com.project.view;

import com.project.entity.evenement.Evenement;
import com.project.util.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Page displaying the details of a specific event.
 * Allows users to proceed to reservation.
 */
public class EvenementDetailPage {

    /**
     * Creates and returns the scene for event details.
     * 
     * @param stage     The primary stage.
     * @param evenement The event to display.
     * @return The Event Detail scene.
     */
    public Scene getScene(Stage stage, Evenement evenement) {
        stage.setTitle("Détails de l'événement - Plateforme Réservation");

        Label title = new Label("Détails de l'événement");

        Label lblNom = new Label("Nom : " + evenement.getNom());
        Label lblDate = new Label("Date : " + evenement.getDate().toString());
        Label lblLieu = new Label("Lieu : " + evenement.getLieu());
        Label lblOrganisateur = new Label("Organisateur ID : " + evenement.getOrganisateurId());

        // ---- Bouton Réserver ----
        Button btnReserver = new Button("Réserver");
        btnReserver.setPrefWidth(140);
        btnReserver.setOnAction(e -> {
            // Ouvrir le formulaire de réservation
            ReservationForm reservationForm = new ReservationForm();
            Scene reservationScene = reservationForm.getScene(stage, evenement);
            stage.setScene(reservationScene);
        });

        // ---- Bouton Retour ----
        Button btnBack = new Button("Retour à la liste");
        btnBack.setPrefWidth(140);
        btnBack.setOnAction(e -> {
            EvenementListPage evenementListPage = new EvenementListPage();
            Scene listScene = evenementListPage.getScene(stage);
            stage.setScene(listScene);
        });

        VBox root = new VBox(10,
                title,
                lblNom,
                lblDate,
                lblLieu,
                lblOrganisateur,
                btnReserver,
                btnBack);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(20));

        return new Scene(root, 400, 320);
    }
}
