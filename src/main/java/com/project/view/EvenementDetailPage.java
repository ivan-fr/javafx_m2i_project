package com.project.view;

import com.project.entity.evenement.Evenement;
import com.project.entity.utilisateur.Utilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EvenementDetailPage {

    public static Scene getScene(Stage stage, Utilisateur user, Evenement evenement) {

        Label title = new Label("Détails de l'événement");

        Label lblNom = new Label("Nom : " + evenement.getNom());
        Label lblDate = new Label("Date : " + evenement.getDate().toString());
        Label lblLieu = new Label("Lieu : " + evenement.getLieu());
        Label lblOrganisateur = new Label("Organisateur ID : " + evenement.getOrganisateurId());

        // ---- Bouton Réserver ----
        Button btnReserver = new Button("Réserver");
        btnReserver.setPrefWidth(140);
        btnReserver.setOnAction(e -> {
            // 👉 Ici tu mettras ta logique réelle de réservation
            // (insertion en BDD, ouverture d'une fenêtre, etc.)

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réservation");
            alert.setHeaderText("Réservation effectuée");
            alert.setContentText("Vous avez réservé une place pour : " + evenement.getNom());
            alert.showAndWait();
        });

        // ---- Bouton Retour ----
        Button btnBack = new Button("Retour à la liste");
        btnBack.setPrefWidth(140);
        btnBack.setOnAction(e -> {
            Scene listScene = EvenementListPage.getScene(stage, user);
            stage.setScene(listScene);
        });

        VBox root = new VBox(10,
                title,
                lblNom,
                lblDate,
                lblLieu,
                lblOrganisateur,
                btnReserver,
                btnBack
        );
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(20));

        return new Scene(root, 400, 320);
    }
}

//package com.project.view;
//
//import com.project.entity.evenement.Evenement;
//import com.project.entity.utilisateur.Utilisateur;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.*;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.stage.Stage;
//
//import java.time.format.DateTimeFormatter;
//
//public class EvenementDetailPage {
//
//    public static Scene getScene(Stage stage, Utilisateur user, Evenement evenement) {
//
//        // ====== FORMATTEUR DE DATE ======
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//
//        // ====== TITRE ======
//        Label title = new Label("Détails de l'événement");
//        title.setFont(Font.font("System", FontWeight.BOLD, 22));
//
//        Label subtitle = new Label(evenement.getNom());
//        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 16));
//        subtitle.setStyle("-fx-text-fill: #666666;");
//
//        VBox headerBox = new VBox(4, title, subtitle);
//        headerBox.setAlignment(Pos.CENTER_LEFT);
//
//        // ====== ZONE INFOS (type "fiche") ======
//        Label lblNom = createInfoLabel("Nom", evenement.getNom());
//        Label lblDate = createInfoLabel("Date", evenement.getDate().format(formatter));
//        Label lblLieu = createInfoLabel("Lieu", evenement.getLieu());
//        Label lblOrganisateur = createInfoLabel("Organisateur ID", String.valueOf(evenement.getOrganisateurId()));
//
//        VBox infoBox = new VBox(8, lblNom, lblDate, lblLieu, lblOrganisateur);
//
//
//        // ====== BOUTONS ======
//        Button btnReserver = new Button("Réserver");
//        btnReserver.setPrefWidth(140);
//        btnReserver.setStyle(
//                "-fx-background-color: #4CAF50;" +
//                        "-fx-text-fill: white;" +
//                        "-fx-font-size: 14px;" +
//                        "-fx-background-radius: 20;"
//        );
//
//        btnReserver.setOnAction(e -> {
//            // TODO: logique vraie de réservation
//            javafx.scene.control.Alert alert =
//                    new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
//            alert.setTitle("Réservation");
//            alert.setHeaderText("Réservation effectuée");
//            alert.setContentText("Vous avez réservé une place pour : " + evenement.getNom());
//            alert.showAndWait();
//        });
//
//        Button btnBack = new Button("Retour à la liste");
//        btnBack.setPrefWidth(140);
//        btnBack.setStyle(
//                "-fx-background-color: transparent;" +
//                        "-fx-border-color: #888888;" +
//                        "-fx-text-fill: #444444;" +
//                        "-fx-font-size: 14px;" +
//                        "-fx-background-radius: 20;" +
//                        "-fx-border-radius: 20;"
//        );
//
//        btnBack.setOnAction(e -> {
//            Scene listScene = EvenementListPage.getScene(stage, user);
//            stage.setScene(listScene);
//        });
//
//        HBox buttonsBox = new HBox(10, btnReserver, btnBack);
//        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
//        buttonsBox.setPadding(new Insets(10, 0, 0, 0));
//
//        // ====== CARTE CENTRALE ======
//        VBox card = new VBox(20, headerBox, infoBox, buttonsBox);
//        card.setPadding(new Insets(20));
//        card.setStyle(
//                "-fx-background-color: white;" +
//                        "-fx-background-radius: 15;" +
//                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);"
//        );
//        card.setMaxWidth(450);
//
//        // ====== ROOT AVEC FOND ======
//        StackPane root = new StackPane(card);
//        root.setPadding(new Insets(30));
//        root.setStyle(
//                "-fx-background-color: linear-gradient(to bottom right, #f5f7fb, #e2e6f0);"
//        );
//        StackPane.setAlignment(card, Pos.CENTER);
//
//        return new Scene(root, 600, 400);
//    }
//
//    /** Petit helper pour afficher "Label : valeur" de façon propre */
//    private static Label createInfoLabel(String label, String value) {
//        Label l = new Label(label + " : " + value);
//        l.setFont(Font.font("System", 14));
//        l.setStyle("-fx-text-fill: #333333;");
//        return l;
//    }
//}

