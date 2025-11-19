package com.project.view;

import com.project.dao.EvenementDAO;
import com.project.entity.EventStats;
import com.project.util.Session;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class StatistiquePage {

    public Scene getScene(Stage stage) {
        stage.setTitle("Statistiques - Plateforme Réservation");

        TableView<EventStats> table = new TableView<>();

        TableColumn<EventStats, String> colNom = new TableColumn<>("Événement");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<EventStats, Integer> colPlaces = new TableColumn<>("Places totales");
        colPlaces.setCellValueFactory(new PropertyValueFactory<>("totalPlaces"));

        TableColumn<EventStats, Integer> colVendus = new TableColumn<>("Tickets vendus");
        colVendus.setCellValueFactory(new PropertyValueFactory<>("totalVendus"));

        TableColumn<EventStats, String> colStd = new TableColumn<>("Standard vendues");
        colStd.setCellValueFactory(e ->
                new SimpleStringProperty(
                    String.valueOf(e.getValue().getVentesParCategorie().getOrDefault("STANDARD", 0))
                )
        );

        TableColumn<EventStats, String> colVip = new TableColumn<>("VIP vendues");
        colVip.setCellValueFactory(e ->
                new SimpleStringProperty(
                    String.valueOf(e.getValue().getVentesParCategorie().getOrDefault("VIP", 0))
                )
        );

        TableColumn<EventStats, String> colTaux = new TableColumn<>("Taux remplissage");
        colTaux.setCellValueFactory(e ->
                new SimpleStringProperty(
                        String.format("%.2f %%", e.getValue().getTauxRemplissage())
                )
        );

        TableColumn<EventStats, String> colCA = new TableColumn<>("Chiffre d’affaires (€)");
        colCA.setCellValueFactory(e ->
                new SimpleStringProperty(
                        String.format("%.2f", e.getValue().getChiffreAffaires())
                )
        );

        table.getColumns().addAll(colNom, colPlaces, colVendus, colStd, colVip, colTaux, colCA);

        int organisateurId = Session.getInstance().getUtilisateur().getId();
        List<EventStats> stats = EvenementDAO.getStatsByOrganisateur(organisateurId);

        table.setItems(FXCollections.observableList(stats));

        Button btnCreation = new Button("Créer une évenement");
        btnCreation.setOnAction(e -> {
            CreateEventPage createEventPage= new CreateEventPage();
            stage.setScene(createEventPage.getScene(stage));
                                       stage.centerOnScreen();
        });

        VBox root = new VBox(10, table, btnCreation);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);


        return new Scene(root, 900, 500);
    }
}
