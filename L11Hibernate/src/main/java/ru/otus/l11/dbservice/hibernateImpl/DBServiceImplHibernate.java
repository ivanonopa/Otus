package ru.otus.l11.dbservice.hibernateImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.otus.l11.datasets.DataSet;
import ru.otus.l11.datasets.EmptyDataSet;
import ru.otus.l11.datasets.PhoneDataSet;
import ru.otus.l11.datasets.UserDataSet;
import ru.otus.l11.dbservice.ConnectionHelper;
import ru.otus.l11.dbservice.DBService;
import ru.otus.l11.dbservice.dao.UserDataSetDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernate<T extends DataSet> implements DBService<T> {
    private final SessionFactory sessionFactory;

    public DBServiceHibernate() {
                sessionFactory = ConnectionHelper.createSessionFactory(ConnectionHelper.getConfiguration());
    }

    public DBServiceHibernate(Configuration configuration) {
        sessionFactory = ConnectionHelper.createSessionFactory(configuration);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public void save(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save((UserDataSet) dataSet);
            //session.save(dataSet);

            session.save(new EmptyDataSet());

        }
    }

    public T read(long id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            T object = dao.read(id);
            //Hibernate.initialize(object);
            return object;
        });
    }

    public T readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    public List<T> getAll() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    public void close() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
