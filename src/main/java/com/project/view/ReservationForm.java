package com.project.view;

import com.project.controller.ReservationController;

import com.project.entity.evenement.CategoryPlace;
import com.project.entity.evenement.Evenement;
import com.project.util.Session;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Form for making a reservation.
 * Allows selecting a category and quantity.
 */
public class ReservationForm {
    public ComboBox<String> categoryCombo = new ComboBox<>();
    public Map<String, CategoryPlace> categoryMap = new HashMap<>();
    private Label titleLabel;
    private Label placeLabel;

    private Spinner<Integer> qtySpinner;

    private ReservationController controller;
    private Evenement event;
    private Button backBtn;

    /**
     * Constructor.
     * Initializes the controller.
     */
    public ReservationForm() {
        controller = new ReservationController();
    }

    /**
     * Creates and returns the reservation scene.
     * 
     * @param stage The primary stage.
     * @param event The event to reserve for.
     * @return The Reservation scene.
     */
    public Scene getScene(Stage stage, Evenement event) {
        stage.setTitle("Réservation - Plateforme Réservation");

        this.event = event;
        titleLabel = new Label("Reservation for: " + event.getNom());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        placeLabel = new Label("");
        backBtn = new Button("← Back to Events");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 5px 12px;");

        qtySpinner = new Spinner<>(1, 20, 1);
        Button reserveBtn = new Button("Reserve");

        for (CategoryPlace cat : event.getCategories()) {
            categoryCombo.getItems().add(cat.getCategorie());
            categoryMap.put(cat.getCategorie(), cat);
        }

        categoryCombo.setPromptText("Select category");
        categoryCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CategoryPlace category = categoryMap.get(newValue);
                int placesDisponibles = controller.getPlacesDisponibles(category.getId());

                placeLabel.setText(
                        "Places restantes : " + placesDisponibles +
                                "\nNombre total de places : " + category.getNbPlaces());
            } else {
                placeLabel.setText("");
            }
        });

        backBtn.setOnAction(e -> {
            EvenementListPage evenementListPage = new EvenementListPage();
            stage.setScene(evenementListPage.getScene(stage));
        });

        reserveBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                reserve(stage);
            }

        });
        VBox root = new VBox(15, backBtn, titleLabel, categoryCombo, placeLabel, qtySpinner, reserveBtn);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        return new Scene(root, 500, 500);
    }

    /**
     * Handles the reservation process.
     * Validates input and proceeds to payment.
     * 
     * @param stage The primary stage.
     */
    private void reserve(Stage stage) {
        int quantity = qtySpinner.getValue();

        String selectedCategory = categoryCombo.getValue();
        if (selectedCategory == null) {
            showErrorMessage("Please select a category");
            return;
        }

        int categoryId = categoryMap.get(selectedCategory).getId();
        int clientId = Session.getInstance().getUtilisateur().getId();

        try {
            controller.checkPlace(clientId, event.getId(), categoryId, quantity);
            // Ouvrir la page paiement
            PaymentForm paymentForm = new PaymentForm();
            stage.setScene(paymentForm.getScene(
                    stage,
                    clientId,
                    event.getId(),
                    categoryId,
                    quantity));
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }

    }

    /**
     * Displays a success alert.
     * 
     * @param message The success message.
     */
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error alert.
     * 
     * @param message The error message.
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
