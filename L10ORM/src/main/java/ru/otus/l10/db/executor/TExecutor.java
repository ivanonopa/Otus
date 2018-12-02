package ru.otus.l10.db.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TExecutor {
    private final Connection connection;
    private static Logger logger = LoggerFactory.getLogger(TExecutor.class);

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            logger.info("Executing stmt - {}", query);
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public <T> T execUpdate(String update, TResultHandler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            logger.info("Executing stmt - {}", update);
            stmt.executeUpdate(update,Statement.RETURN_GENERATED_KEYS);
            ResultSet result = stmt.getGeneratedKeys();
            return handler.handle(result);
        }
    }

    Connection getConnection() {
        return connection;
    }

}
