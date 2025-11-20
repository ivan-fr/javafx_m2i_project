package com.project.view.event;

import com.project.controller.EvenementController;
import com.project.entity.evenement.Evenement;
import com.project.view.reservation.HistoriqueReservationsPage;
import com.project.view.LayoutUtil;
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

/**
 * Page displaying the list of all events.
 * Includes filtering and sorting functionalities.
 */
public class EvenementListPage {

    /**
     * Creates and returns the scene for the event list.
     * 
     * @param stage The primary stage.
     * @return The Event List scene.
     */
    public Scene getScene(Stage stage) {
        stage.setTitle("Liste des événements - Plateforme Réservation");

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

        VBox topBox = new VBox(10, filterBox);
        topBox.setPadding(new Insets(10));

        Button btnHistorique = new Button("Consulter l'historique");
        btnHistorique.setAlignment(Pos.CENTER_RIGHT);
        btnHistorique.setOnAction(e -> {
            HistoriqueReservationsPage historiqueReservationsPage = new HistoriqueReservationsPage();
            Scene historique = historiqueReservationsPage.getScene(stage);
            stage.setScene(historique);
        });

        topBox.getChildren().add(btnHistorique);

        // --- ListView ---
        ListView<Evenement> listView = new ListView<>();

        EvenementController evenementController = new EvenementController();
        List<Evenement> evenements = evenementController.getAllEvenements();
        ObservableList<Evenement> masterData = FXCollections.observableArrayList(evenements);

        // Filtrage
        FilteredList<Evenement> filteredData = new FilteredList<>(masterData, ev -> true);

        typeFilter.valueProperty().addListener(
                (obs, oldVal, newVal) -> appliquerFiltres(filteredData, typeFilter.getValue(), lieuFilter.getText()));

        lieuFilter.textProperty().addListener(
                (obs, oldVal, newVal) -> appliquerFiltres(filteredData, typeFilter.getValue(), lieuFilter.getText()));

        // Tri
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        sortedData.setComparator(Comparator.comparing(Evenement::getDate));

        listView.setItems(sortedData);

        // Double-clic
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Evenement selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    EvenementDetailPage evenementDetailPage = new EvenementDetailPage();
                    Scene detailScene = evenementDetailPage.getScene(stage, selected);
                    stage.setScene(detailScene);
                }
            }
        });

        // Sélection auto
        if (!sortedData.isEmpty()) {
            listView.getSelectionModel().selectFirst();
        }

        // Créer le contenu principal (centre)
        VBox mainContent = new VBox(10, topBox, listView);
        mainContent.setPadding(new Insets(10));

        // Utiliser le layout global avec header + footer + déconnexion
        BorderPane root = LayoutUtil.createLayout(stage, mainContent, "Liste des événements");
        return new Scene(root, LayoutUtil.PARAM_WIDTH,  LayoutUtil.PARAM_HEIGHT);
    }

    /**
     * Applies filters to the event list.
     * 
     * @param filteredData The list to filter.
     * @param typeChoisi   The selected event type.
     * @param lieuTexte    The text for location filtering.
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
