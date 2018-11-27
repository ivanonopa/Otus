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
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            logger.info("Executing stmt - {}", update);
            return stmt.getUpdateCount();
        }
    }

    Connection getConnection() {
        return connection;
    }

}
