package ru.otus.l10.db;

import ru.otus.l10.dataset.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService<T extends DataSet> extends AutoCloseable {
    void prepareTables() throws SQLException;

    void save(T user) throws SQLException;

    T load(long id) throws SQLException;

    List<T> getAllData() throws SQLException;
}
