package com.project.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    private static Connection conn;


    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connexion établie avec succès!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void fermerConnexion() {
        try {
         /*   if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }*/
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            System.out.println("Connexion fermée avec succès!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
