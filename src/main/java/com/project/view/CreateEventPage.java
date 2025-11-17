package com.project.view;

import com.project.entity.evenement.*;
import com.project.controller.EvenementController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CreateEventPage {

    public static Scene getScene(Stage stage, int organisateurId) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(25));

        Label title = new Label("Créer un nouvel événement");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        grid.add(title, 0, 0, 2, 1);

        TextField nomField = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField lieuField = new TextField();

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("CONCERT", "SPECTACLE", "CONFERENCE");

        TextField prixStd = new TextField();
        prixStd.setPromptText("Prix Standard");

        TextField placesStd = new TextField();
        placesStd.setPromptText("Places Standard");

        TextField prixVIP = new TextField();
        prixVIP.setPromptText("Prix VIP");

        TextField placesVIP = new TextField();
        placesVIP.setPromptText("Places VIP");

        Label message = new Label();
        message.setStyle("-fx-text-fill: red;");

        Button btnCreate = new Button("Créer l'événement");
        btnCreate.setStyle("-fx-background-color: #0080ff; -fx-text-fill: white;");

        grid.add(new Label("Nom :"), 0, 1);
        grid.add(nomField, 1, 1);

        grid.add(new Label("Date :"), 0, 2);
        grid.add(datePicker, 1, 2);

        grid.add(new Label("Lieu :"), 0, 3);
        grid.add(lieuField, 1, 3);

        grid.add(new Label("Type :"), 0, 4);
        grid.add(typeCombo, 1, 4);

        grid.add(new Label("Standard :"), 0, 5);
        grid.add(new HBox(10, prixStd, placesStd), 1, 5);

        grid.add(new Label("VIP :"), 0, 6);
        grid.add(new HBox(10, prixVIP, placesVIP), 1, 6);

        grid.add(btnCreate, 1, 7);
        grid.add(message, 1, 8);

        // ==========================
        // BOUTON
        // ==========================
        btnCreate.setOnAction(event -> {

            String nom = nomField.getText().trim();
            LocalDate date = datePicker.getValue();
            String lieu = lieuField.getText().trim();
            String type = typeCombo.getValue();

            if (nom.isEmpty() || lieu.isEmpty() || date == null || type == null) {
                message.setText("Tous les champs doivent être remplis.");
                return;
            }

            if (prixStd.getText().isEmpty() || placesStd.getText().isEmpty() ||
                prixVIP.getText().isEmpty() || placesVIP.getText().isEmpty()) {

                message.setText("Veuillez remplir toutes les catégories.");
                return;
            }

            double prixStandard, prixVip;
            int placesStandard, placesVip;

            try {
                prixStandard = Double.parseDouble(prixStd.getText());
                placesStandard = Integer.parseInt(placesStd.getText());
                prixVip = Double.parseDouble(prixVIP.getText());
                placesVip = Integer.parseInt(placesVIP.getText());
            } catch (NumberFormatException ex) {
                message.setText("Les prix et les places doivent être des nombres valides.");
                return;
            }

            // 🔥 Création de l'événement
            Evenement evt = switch (type) {
                case "CONCERT" -> new Concert(nom, date.atStartOfDay(), lieu, organisateurId);
                case "SPECTACLE" -> new Spectacle(nom, date.atStartOfDay(), lieu, organisateurId);
                case "CONFERENCE" -> new Conference(nom, date.atStartOfDay(), lieu, organisateurId);
                default -> null;
            };

            // 🔥 Créer les catégories et les ajouter à l'événement AVANT de sauvegarder
            CategoryPlace standard = new CategoryPlace("STANDARD", placesStandard, prixStandard);
            CategoryPlace vip = new CategoryPlace("VIP", placesVip, prixVip);

            evt.getCategories().add(standard);
            evt.getCategories().add(vip);

            // 🔥 Créer l'événement via le controller
            EvenementController controller = new EvenementController();
            boolean success = controller.createEvenement(evt);

            if (success) {
                System.out.println("ID even inserted = " + evt.getId());
                message.setStyle("-fx-text-fill: green;");
                message.setText("Événement créé avec succès !");
            } else {
                message.setStyle("-fx-text-fill: red;");
                message.setText("Erreur lors de la création de l'événement.");
            }
        });

        return new Scene(grid, 600, 500);
    }
}
