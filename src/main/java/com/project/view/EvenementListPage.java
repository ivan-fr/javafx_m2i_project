package com.project.view;

import com.project.controller.EvenementController;
import com.project.entity.evenement.Evenement;
import com.project.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class EvenementListPage {

    public static Scene getScene(Stage stage) {

        // --- Titre ---
        Label welcomeLabel = new Label("Bonjour " + Session.getInstance().getUtilisateur().getNom() + " - Liste des événements");

        // --- Filtres ---
        // Filtre type d'événement
        Label typeLabel = new Label("Type :");
        ComboBox<String> typeFilter = new ComboBox<>();
        typeFilter.getItems().addAll("Tous", "CONCERT", "SPECTACLE", "CONFERENCE");
        typeFilter.setValue("Tous");

        // Filtre lieu
        Label lieuLabel = new Label("Lieu :");
        TextField lieuFilter = new TextField();
        lieuFilter.setPromptText("Ex : Paris, Lyon...");

        HBox filterBox = new HBox(10, typeLabel, typeFilter, lieuLabel, lieuFilter);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        VBox topBox = new VBox(10, welcomeLabel, filterBox);
        topBox.setPadding(new Insets(10));

        // --- ListView des événements ---
        ListView<Evenement> listView = new ListView<>();

        // Données brutes depuis la BDD
        EvenementController evenementController = new EvenementController();
        List<Evenement> evenements = evenementController.getAllEvenements();
        ObservableList<Evenement> masterData = FXCollections.observableArrayList(evenements);

        // Filtrage
        FilteredList<Evenement> filteredData = new FilteredList<>(masterData, ev -> true);

        // Mise à jour de la condition de filtre à chaque changement
        typeFilter.valueProperty().addListener((obs, oldVal, newVal) -> {
            appliquerFiltres(filteredData, typeFilter.getValue(), lieuFilter.getText());
        });

        lieuFilter.textProperty().addListener((obs, oldVal, newVal) -> {
            appliquerFiltres(filteredData, typeFilter.getValue(), lieuFilter.getText());
        });

        // Tri par date
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        sortedData.setComparator(Comparator.comparing(Evenement::getDate));

        listView.setItems(sortedData);

        // Double-clic -> page de détails
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Evenement selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    Scene detailScene = EvenementDetailPage.getScene(stage, selected);
                    stage.setScene(detailScene);
                }
            }
        });

        // Option : sélectionner le premier élément si la liste n'est pas vide
        if (!sortedData.isEmpty()) {
            listView.getSelectionModel().selectFirst();
        }

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(listView);
        BorderPane.setMargin(listView, new Insets(10));

        return new Scene(root, 800, 500);
    }

    /**
     * Applique les filtres de type et de lieu sur la liste filtrée.
     */
    private static void appliquerFiltres(FilteredList<Evenement> filteredData,
                                         String typeChoisi,
                                         String lieuTexte) {

        String typeFiltre = (typeChoisi == null) ? "Tous" : typeChoisi;
        String lieuFiltre = (lieuTexte == null) ? "" : lieuTexte.trim().toLowerCase();

        filteredData.setPredicate(ev -> {

            // Filtre type
            if (!"Tous".equalsIgnoreCase(typeFiltre)) {
                // Type déduit de la classe concrète : Concert, Spectacle, Conference...
                String typeEvent = ev.getClass().getSimpleName().toUpperCase();
                if (!typeEvent.equals(typeFiltre)) {
                    return false;
                }
            }

            // Filtre lieu (contient le texte saisi, insensible à la casse)
            if (!lieuFiltre.isEmpty()) {
                if (ev.getLieu() == null ||
                        !ev.getLieu().toLowerCase().contains(lieuFiltre)) {
                    return false;
                }
            }

            return true;
        });
    }
}
