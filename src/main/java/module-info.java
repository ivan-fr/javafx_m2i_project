module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.project.dao to javafx.base;

    opens com.project to javafx.fxml;

    exports com.project;
    exports com.project.controller;
    exports com.project.dao;

    exports com.project.entity.evenement;
    exports com.project.entity.utilisateur;
    exports com.project.entity.reservation;
    exports com.project.exception;
    exports com.project.util;
    exports com.project.view;
    exports com.project.view.event;
    exports com.project.view.authentification;
    exports com.project.view.reservation;
    exports com.project.view.statistique;
    opens com.project.entity.evenement to javafx.base;

    opens com.project.entity.reservation to javafx.base;
}