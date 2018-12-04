package ru.otus.l10.db;

import ru.otus.l10.dataset.DataSet;
import ru.otus.l10.db.executor.TExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServiceImpl<T extends DataSet> implements DBService<T> {
    private final Connection connection;
    private final Class<T> clazz;
    private final Table table;
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    private static final String SELECT_USER_BY_ID = "select * from %s where id=\'%d\'";
    private static final String SELECT_ALL = "select * from %s";

    public DBServiceImpl(Class<T> clazz) {
        this.clazz = clazz;
        table = clazz.getAnnotation(Table.class);;
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        logger.info("Connection closed. Bye!");
    }

    @Override
    public void prepareTables() throws SQLException {

        StringBuilder createTable = new StringBuilder("create table if not exists ").append(table.name()).append(" (id bigint auto_increment,");
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            createTable.append(field.getName()).append(" ");
            switch (field.getType().getName()) {
                case "java.lang.String":
                    createTable.append(" varchar(256), ");
                    break;
                case "java.lang.Integer":
                    createTable.append(" bigint, ");
                    break;
                default:
                    throw new UnsupportedOperationException("not a supported field type");
            }
            field.setAccessible(false);
        }

        createTable.append("primary key (id))");
        TExecutor exec = new TExecutor(getConnection());
        exec.execUpdate(createTable.toString(),result-> null);
        logger.info("Table created");
    }


    public List<T> getAllData() throws SQLException {
        TExecutor execT = new TExecutor(getConnection());
        String selectAll = String.format(SELECT_ALL,table.name());
        logger.info("Executing - {}", selectAll);
        return execT.execQuery(selectAll, result -> {
            List<T> dataList = new ArrayList<>();
            while (result.next()) {
                dataList.add(instantiateAndSetFields(result));
            }
            return dataList;
        });
    }

    @Override
    public void save(T user) throws SQLException {
        TExecutor exec = new TExecutor(getConnection());
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        try {
            Field[] fields = user.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                names.append(field.getName()).append(",");
                if (isString(field)) values.append("\'");
                values.append(String.valueOf(field.get(user)));
                if (isString(field)) values.append("\'");
                values.append(",");
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        StringBuilder query = new StringBuilder();
        query.append("insert into ").append(table.name()).append(" (");
        names.deleteCharAt(names.length() - 1);
        values.deleteCharAt(values.length() - 1);
        query.append(names).append(") values (").append(values).append(")");

        Integer id = exec.execUpdate(query.toString(), resultSet -> {
            resultSet.next();
            return resultSet.getInt("id");
        });

        setId(user, id);
    }

    public T load(long id) throws SQLException {
        TExecutor execT = new TExecutor(getConnection());
        logger.info("Executing - {}", String.format(SELECT_USER_BY_ID, table.name(), id));
        return execT.execQuery(String.format(SELECT_USER_BY_ID, table.name(), id), result -> {
            result.next();
            return instantiateAndSetFields(result);
        });
    }

    private Connection getConnection() {
        return connection;
    }

    private T instantiateAndSetFields(ResultSet result) throws SQLException {
        T newT = ReflectionHelper.instantiate(clazz);
        if (newT==null ) throw new UnsupportedOperationException("can't instantiate object of " + clazz.getName());

        setId(newT, result.getInt("id"));

        for (Field field : clazz.getDeclaredFields()) {

            switch (field.getType().getName()) {
                case "java.lang.String":
                    ReflectionHelper.setFieldValue(newT, field.getName(), result.getString(field.getName()));
                    break;
                case "java.lang.Integer":
                    ReflectionHelper.setFieldValue(newT, field.getName(), result.getInt(field.getName()));
                    break;
                default:
                    throw new UnsupportedOperationException("not a supported field type");
            }
        }
        return newT;
    }

    private void setId(T o, Object value){
        try {
            Field id = clazz.getSuperclass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(o, value);
            id.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private boolean isString(Field field) {
        return field.getType().getName().equals("java.lang.String");
    }
}
