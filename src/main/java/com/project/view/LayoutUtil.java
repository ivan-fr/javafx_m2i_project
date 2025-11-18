package com.project.view;

import com.project.util.Session;
import com.project.entity.utilisateur.Utilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LayoutUtil {
    public static BorderPane createLayout(Stage stage, Node centerContent, String pageTitle) {

        // HEADER
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(15);

        Label titleLabel = new Label(pageTitle);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        header.getChildren().add(titleLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Utilisateur user = Session.getInstance().getUtilisateur();
        if (user != null) {
            Label userLabel = new Label("Bonjour, " + user.getNom());
            Button logoutBtn = new Button("Déconnexion");

            logoutBtn.setOnAction(e -> {
                Session.getInstance().clear();
                stage.setScene(LoginPage.getScene(stage));
            });

            header.getChildren().addAll(spacer, userLabel, logoutBtn);
        }

        // FOOTER
        HBox footer = new HBox();
        footer.setPadding(new Insets(5));
        footer.setAlignment(Pos.CENTER);
        footer.getChildren().add(new Label("© 2025 - JavaFX Projet M2I"));

        // Assemble
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(centerContent);
        root.setBottom(footer);

        return root;
    }

}

