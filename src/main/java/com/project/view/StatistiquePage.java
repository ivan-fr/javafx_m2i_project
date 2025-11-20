package com.project.view;

import com.project.dao.EvenementDAO;
import com.project.entity.EventStats;
import com.project.util.Session;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class StatistiquePage {

    public Scene getScene(Stage stage) {
        stage.setTitle("Statistiques - Plateforme Réservation");

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Statistiques de vos événements");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        content.getChildren().add(title);

        int organisateurId = Session.getInstance().getUtilisateur().getId();
        List<EventStats> statsList = EvenementDAO.getStatsByOrganisateur(organisateurId);

        // Conteneur des cartes
        VBox cardsContainer = new VBox(15);

        for (EventStats stats : statsList) {

            VBox card = new VBox(5);
            card.setPadding(new Insets(10));
            card.setStyle(
                    "-fx-background-color: #f3f3f3; -fx-border-color: #cccccc; -fx-border-radius: 6px; -fx-background-radius: 6px;");

            card.getChildren().add(new Label("Événement : " + stats.getNom()));
            card.getChildren().add(new Label("Places totales : " + stats.getTotalPlaces()));
            card.getChildren().add(new Label("Tickets vendus : " + stats.getTotalVendus()));
            card.getChildren().add(new Label("Standard vendus : " +
                    stats.getVentesParCategorie().getOrDefault("STANDARD", 0)));
            card.getChildren().add(new Label("VIP vendus : " +
                    stats.getVentesParCategorie().getOrDefault("VIP", 0)));
            card.getChildren().add(new Label(
                    String.format("Taux de remplissage : %.2f %%", stats.getTauxRemplissage())));
            card.getChildren().add(new Label(
                    String.format("Chiffre d'affaires : %.2f €", stats.getChiffreAffaires())));

            cardsContainer.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(cardsContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");

        Button btnCreation = new Button("Créer un événement");
        btnCreation.setOnAction(e -> stage.setScene(new CreateEventPage().getScene(stage)));

        content.getChildren().addAll(scroll, btnCreation);

        BorderPane layout = LayoutUtil.createLayout(stage, content, "Statistiques");

        return new Scene(layout, 900, 600);
    }
}
