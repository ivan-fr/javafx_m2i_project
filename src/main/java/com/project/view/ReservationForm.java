package com.project.view;

import com.project.Main;
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

public class ReservationForm {
    public ComboBox<String> categoryCombo = new ComboBox<>();
    public Map<String, Integer> categoryMap = new HashMap<>();
    private Label titleLabel;
    private Label messageLabel;
    private Spinner<Integer> qtySpinner;

    private ReservationController controller;
    private Evenement event;


    public ReservationForm() {
        controller = new ReservationController();
    }

    public Scene getScene(Stage stage, Evenement event) {
        this.event = event;
        titleLabel = new Label("Reservation for: " + event.getNom());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: red;");

        qtySpinner = new Spinner<>(1, 20, 1);
        Button reserveBtn = new Button("Reserve");


        for (CategoryPlace cat : event.getCategories()) {
            categoryCombo.getItems().add(cat.getCategorie());
            categoryMap.put(cat.getCategorie(), cat.getId());
        }

        categoryCombo.setPromptText("Select category");


        reserveBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                reserve();
            }


        });
        VBox root = new VBox(15, titleLabel, categoryCombo, qtySpinner, reserveBtn,messageLabel ); root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        return new Scene(root, 500, 500);
    }

    private void reserve() {
        int quantity = qtySpinner.getValue();

        String selectedCategory = categoryCombo.getValue();
        if (selectedCategory == null) {
            messageLabel.setText("Please select a category!");
            return;
        }

        int selectedCategoryId = categoryMap.get(selectedCategory);
        boolean result = controller.reserve(Session.getUtilisateur().getId(), event.getId(), selectedCategoryId, quantity);

        messageLabel.setText(result ? "Reservation successful!" : "Reservation failed.");
    }

}
