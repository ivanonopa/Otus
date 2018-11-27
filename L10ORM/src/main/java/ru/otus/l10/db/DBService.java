package ru.otus.l10.db;

import ru.otus.l10.dataset.DataSet;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    void prepareTables() throws SQLException;

    <T extends DataSet> void save(T user) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    void listAllData() throws SQLException;
}
