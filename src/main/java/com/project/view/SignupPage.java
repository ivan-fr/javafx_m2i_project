package com.project.view;

import com.project.dao.UtilisateurDAO;
import com.project.entity.utilisateur.Client;
import com.project.entity.utilisateur.Organisateur;
import com.project.entity.utilisateur.Utilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignupPage {

    public static Scene getScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Nom
        Label nomLabel = new Label("Nom :");
        TextField nomField = new TextField();
        grid.add(nomLabel, 0, 0);
        grid.add(nomField, 1, 0);

        // Email
        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        grid.add(emailLabel, 0, 1);
        grid.add(emailField, 1, 1);

        // Mot de passe
        Label pwdLabel = new Label("Mot de passe :");
        PasswordField pwdField = new PasswordField();
        grid.add(pwdLabel, 0, 2);
        grid.add(pwdField, 1, 2);

        // Type de compte
        Label typeLabel = new Label("Type de compte :");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("CLIENT", "ORGANISATEUR");
        typeComboBox.setValue("CLIENT");
        grid.add(typeLabel, 0, 3);
        grid.add(typeComboBox, 1, 3);

        // Message
        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 4);

        // Bouton inscription
        Button btnSignup = new Button("S'inscrire");
        grid.add(btnSignup, 1, 5);

        btnSignup.setOnAction(e -> {
            String nom = nomField.getText().trim();
            String email = emailField.getText().trim();
            String password = pwdField.getText();
            String typeCompte = typeComboBox.getValue();

            // Faire la logique de validation ici :)

            Utilisateur utilisateur;
            if (typeCompte.equals("CLIENT")) {
                utilisateur = new Client(nom, email, password);
            } else {
                utilisateur = new Organisateur(nom, email, password);
            }

            try {
                UtilisateurDAO dao = new UtilisateurDAO();
                dao.ajouter(utilisateur);
                messageLabel.setText("Compte créé avec succès !");
                Scene loginScene = LoginPage.getScene(stage);
                stage.setScene(loginScene);
            } catch (Exception ex) {
                messageLabel.setText("Erreur lors de la création du compte !");
                ex.printStackTrace();
            }
        });

        // Bouton retour
        Button btnBack = new Button("Retour à la connexion");
        grid.add(btnBack, 1, 6);

        btnBack.setOnAction(e -> {
            Scene loginScene = LoginPage.getScene(stage);
            stage.setScene(loginScene);
        });

        return new Scene(grid, 400, 300);
    }
}
