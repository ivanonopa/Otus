package ru.otus.l11.dbservice;

import ru.otus.l11.datasets.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService<T extends DataSet> extends AutoCloseable {

    void save(T user) throws SQLException;

    T read(long id) throws SQLException;

    T readByName(String name) throws SQLException;

    List<T> getAll() throws SQLException;

    String getLocalStatus();

    void close() throws Exception ;

}
