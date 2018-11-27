package ru.otus.l10.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {
    private static final String URL = "jdbc:h2:mem:aaaTest:";

    static Connection getConnection() {
        try {
            Driver driver = new org.h2.Driver();
            DriverManager.registerDriver(driver);
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
