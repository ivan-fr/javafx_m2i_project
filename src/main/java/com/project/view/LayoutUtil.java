package com.project.view;

import com.project.util.Session;
import com.project.entity.utilisateur.Utilisateur;
import com.project.view.authentification.LoginPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Utility class for creating the common application layout.
 * Provides a standard header and footer.
 */
public class LayoutUtil {
    /**
     * The default width for the application window.
     */
    public final static int PARAM_WIDTH = 900;

    /**
     * The default height for the application window.
     */
    public final static int PARAM_HEIGHT = 600;

    /**
     * Creates a BorderPane with a standard header and footer.
     * The header includes the page title and user session info.
     *
     * @param stage         The primary stage.
     * @param centerContent The content to display in the center.
     * @param pageTitle     The title of the page.
     * @return The constructed BorderPane.
     */
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
                LoginPage loginPage = new LoginPage();
                stage.setScene(loginPage.getScene(stage));
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
