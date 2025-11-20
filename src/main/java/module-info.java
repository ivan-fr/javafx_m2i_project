module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.project.entity to javafx.base;
    opens com.project.dao to javafx.base;

    opens com.project to javafx.fxml;
    exports com.project;
}