package com.project.view;


import com.project.dao.UtilisateurDAO;
import com.project.entity.utilisateur.Utilisateur;
import com.project.entity.utilisateur.Client;
import com.project.entity.utilisateur.Organisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage {
    public static Scene getScene(Stage stage) {
        // GridPane pour layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Email
        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);

        // Mot de passe
        Label pwdLabel = new Label("Mot de passe :");
        PasswordField pwdField = new PasswordField();
        grid.add(pwdLabel, 0, 1);
        grid.add(pwdField, 1, 1);

        // Message d'erreur
        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 2);

        // Bouton de connexion
        Button btnLogin = new Button("Se connecter");
        grid.add(btnLogin, 1, 3);

        btnLogin.setOnAction(e -> {
            String email = emailField.getText();
            String password = pwdField.getText();
            

            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs !");
                return;
            }

            Utilisateur user = UtilisateurDAO.login(email, password);
            if (user == null) {
                messageLabel.setText("Email ou mot de passe incorrect !");
                return;
            }

            messageLabel.setText("Connexion réussie !");

            // Rediréction selon le type de l'utilisateur
            if (user instanceof Organisateur organisateur) {
                // Page de création d'événement
                Scene sceneEvent = CreateEventPage.getScene(stage, organisateur.getId());
                stage.setScene(sceneEvent);
                stage.show();
                return;
            }

            if (user instanceof Client client) {
                // Redirection vers la page liste des événements
                Scene eventListScene = EvenementListPage.getScene(stage, user);
                stage.setScene(eventListScene);
                return;
            }


        });

        // Bouton création compte
        Button btnRegister = new Button("Créer un compte");
        grid.add(btnRegister, 1, 4);
        btnRegister.setOnAction(e -> {
            Scene signupScene = SignupPage.getScene(stage);
            stage.setScene(signupScene);
        });

        return new Scene(grid, 400, 300);
    }
}