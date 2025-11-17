package com.project;

import com.project.entity.utilisateur.Utilisateur;
import com.project.view.LoginPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion - Plateforme Réservation");
        Scene scene = LoginPage.getScene(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
