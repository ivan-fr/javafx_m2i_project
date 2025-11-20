package com.project;

import com.project.view.LoginPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point of the JavaFX application.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * Sets up the initial scene with the login page.
     * 
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        LoginPage loginPage = new LoginPage();
        Scene scene = loginPage.getScene(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method to launch the application.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
