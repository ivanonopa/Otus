package ru.otus.l10.db;

import ru.otus.l10.dataset.DataSet;
import ru.otus.l10.db.executor.TExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServiceImpl implements DBService {
    private final Connection connection;
    private static Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint auto_increment, name varchar(256), age bigint, height bigint, primary key (id))";
    private static final String SELECT_USER_BY_ID = "select * from user where id=\'%d\'";
    private static final String SELECT_ALL = "select * from user";

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        logger.info("Connection closed. Bye!");
    }

    @Override
    public void prepareTables() throws SQLException {
        TExecutor exec = new TExecutor(getConnection());
        exec.execUpdate(CREATE_TABLE_USER);
        logger.info("Table created");
    }

    public void listAllData() throws SQLException {
        TExecutor execT = new TExecutor(getConnection());
        logger.info("Executing - " + SELECT_ALL);
        execT.execQuery(SELECT_ALL, result -> {

            while (result.next()) {
                logger.info(result.getInt("id") + "." + result.getString("name") + " " + result.getInt("age"));
            }
            return null;
        });
    }

    public <T extends DataSet> void save(T user) throws SQLException {
        TExecutor exec = new TExecutor(getConnection());
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        try {
            Field[] fields = user.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    names.append(field.getName()).append(",");
                    if (field.getType().getName().equals("java.lang.String")) values.append("\'");
                    values.append(String.valueOf(field.get(user)));
                    if (field.getType().getName().equals("java.lang.String")) values.append("\'");
                    values.append(",");
                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        StringBuilder query = new StringBuilder();
        query.append("insert into user (");
        names.deleteCharAt(names.length() - 1);
        values.deleteCharAt(values.length() - 1);
        query.append(names).append(") values (").append(values).append(")");

        logger.info("Executing - " + query.toString());
        exec.execUpdate(query.toString());
        logger.info("User " + ReflectionHelper.callMethod(user, "getName") + " saved in db.");
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        TExecutor execT = new TExecutor(getConnection());
        logger.info("Executing - " + String.format(SELECT_USER_BY_ID, id));
        return execT.execQuery(String.format(SELECT_USER_BY_ID, id), result -> {
            result.next();
            List<Object> argList = new ArrayList<>();
            Field[] fields = clazz.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                switch (fields[i].getType().getName()) {
                    case "java.lang.String":
                        argList.add(result.getString(i + 1));
                        break;
                    case "java.lang.Integer":
                        argList.add(result.getInt(i + 1));
                        break;
                }
            }
            Object[] args = new Object[argList.size()];
            argList.toArray(args);
            return ReflectionHelper.instantiate(clazz, args);
        });
    }

    private Connection getConnection() {
        return connection;
    }
}
