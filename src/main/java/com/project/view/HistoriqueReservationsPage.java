package com.project.view;

import java.util.List;

import com.project.dao.ReservationDAO;
import com.project.entity.Reservation;
import com.project.util.Session;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        table.getColumns().addAll(colEvent, colDateEvt, colCategorie, colPrix, colDateRes);

        // Charger depuis DAO
        List<Reservation> reservations = ReservationDAO.getReservationsByClientId(clientId);
        table.setItems(FXCollections.observableList(reservations));

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> stage.setScene(EvenementListPage.getScene(stage)));

        VBox root = new VBox(10, table, btnRetour);
        root.setPadding(new Insets(10));

        return new Scene(root, 800, 500);
    }
}
