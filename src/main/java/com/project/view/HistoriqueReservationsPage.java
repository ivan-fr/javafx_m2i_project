package com.project.view;

import java.util.List;

import com.project.controller.ReservationController;
import com.project.dao.ReservationDAO;
import com.project.entity.Reservation;
import com.project.exception.AnnulationTardiveException;
import com.project.util.Session;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoriqueReservationsPage {

    public static Scene getScene(Stage stage) {

        int clientId = Session.getInstance().getUtilisateur().getId();

        TableView<Reservation> table = new TableView<>();

        TableColumn<Reservation, String> colEvent = new TableColumn<>("Événement");
        colEvent.setCellValueFactory(new PropertyValueFactory<>("evenementNom"));

        TableColumn<Reservation, String> colDateEvt = new TableColumn<>("Date de l'événement");
        colDateEvt.setCellValueFactory(r ->
                new SimpleStringProperty(r.getValue().getEvenementDate().toString())
        );

        TableColumn<Reservation, String> colCategorie = new TableColumn<>("Catégorie");
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        TableColumn<Reservation, String> colPrix = new TableColumn<>("Total payé (€)");
        colPrix.setCellValueFactory(r ->
                new SimpleStringProperty(String.valueOf(r.getValue().getPrixTotal()))
        );

        TableColumn<Reservation, String> colDateRes = new TableColumn<>("Réservé le");
        colDateRes.setCellValueFactory(r ->
                new SimpleStringProperty(r.getValue().getDateReservation().toString())
        );

        // --- Colonne Action avec bouton "Annuler" ---
        // Controller de réservation
        ReservationController reservationController = new ReservationController();

        TableColumn<Reservation, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(col -> new TableCell<>() {

            private final Button btn = new Button("Annuler");

            {
                btn.setStyle(
                        "-fx-background-color: #e63946;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 5px;" +
                                "-fx-padding: 5 10 5 10;"
                );

                btn.setOnAction(e -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    handleCancel(reservation, clientId, reservationController, table);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    VBox wrapper = new VBox(btn);
                    wrapper.setStyle("-fx-alignment: center;");
                    setGraphic(wrapper);
                }
            }
        });

        table.getColumns().addAll(colEvent, colDateEvt, colCategorie, colPrix, colDateRes, colAction);

        // Charger depuis DAO
        refreshTable(table, clientId);

        // Bouton Annuler
        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> stage.setScene(EvenementListPage.getScene(stage)));
        VBox root = new VBox(10, table, btnRetour);
        root.setPadding(new Insets(10));

        return new Scene(root, 800, 550);
    }

    private static void refreshTable(TableView<Reservation> table, int clientId) {
        List<Reservation> reservations = ReservationDAO.getReservationsByClientId(clientId);
        table.setItems(FXCollections.observableList(reservations));
    }
    // Gestion du clic sur "Annuler"
    private static void handleCancel(Reservation reservation,
                                     int clientId,
                                     ReservationController controller,
                                     TableView<Reservation> table) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Annuler la réservation");
        confirm.setHeaderText(null);
        confirm.setContentText("Voulez-vous vraiment annuler cette réservation ?");
        confirm.getDialogPane().setGraphic(null);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES || response == ButtonType.OK) {
                try {
                    controller.annulerReservation(reservation.getId(), clientId);
                    showInfo("Annulation réussie", "La réservation a été annulée.");
                    refreshTable(table, clientId);
                } catch (AnnulationTardiveException ex) {
                    showError("Annulation tardive", ex.getMessage());
                } catch (IllegalArgumentException | IllegalStateException ex) {
                    showError("Erreur", ex.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showError("Erreur", "Une erreur est survenue lors de l'annulation.");
                }
            }
        });
    }

    private static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setGraphic(null);
        alert.showAndWait();
    }

    private static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setGraphic(null);
        alert.showAndWait();
    }
}
