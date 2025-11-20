package com.project.view;

import com.project.controller.ReservationController;

import com.project.dao.PaymentDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Form for entering payment details.
 * Handles payment processing and reservation confirmation.
 */
public class PaymentForm {

    private ReservationController controller = new ReservationController();

    /**
     * Creates and returns the payment scene.
     * 
     * @param stage      The primary stage.
     * @param clientId   The client ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantity   The number of places to reserve.
     * @return The Payment scene.
     */
    public Scene getScene(Stage stage,
            int clientId,
            int eventId,
            int categoryId,
            int quantity) {
        stage.setTitle("Paiement - Plateforme Réservation");

        Label title = new Label("Payment Information");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nom sur la carte");

        TextField cardField = new TextField();
        cardField.setPromptText("Numéro de carte (16 chiffres)");

        Button payBtn = new Button("Payer");

        payBtn.setOnAction(e -> {
            try {
                PaymentDetails details = new PaymentDetails(
                        nameField.getText(),
                        cardField.getText());

                controller.processPaymentAndReservation(
                        clientId, eventId, categoryId, quantity, details);

                showSuccess("Paiement réussi ! Réservation confirmée.", () -> {
                    EvenementListPage evenementListPage = new EvenementListPage();
                    stage.setScene(evenementListPage.getScene(stage));
                });

            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        VBox root = new VBox(15, title, nameField, cardField, payBtn);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        return new Scene(root, 400, 350);
    }

    /**
     * Displays a success alert and executes a callback.
     * 
     * @param msg  The success message.
     * @param onOk The callback to execute when OK is clicked.
     */
    private void showSuccess(String msg, Runnable onOk) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        // Create your custom buttons
        ButtonType okBtn = new ButtonType("ok");

        // Clear default buttons and add your own
        alert.getButtonTypes().setAll(okBtn);

        // Show and get user result
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == okBtn) {
                onOk.run();
            }
        }
    }

    /**
     * Displays an error alert.
     * 
     * @param msg The error message.
     */
    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
